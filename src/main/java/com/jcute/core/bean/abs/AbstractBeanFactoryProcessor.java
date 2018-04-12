package com.jcute.core.bean.abs;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.LinkedHashSet;
import java.util.Map.Entry;
import java.util.Set;

import com.jcute.core.bean.BeanDefinition;
import com.jcute.core.bean.BeanFactory;
import com.jcute.core.bean.BeanFactoryProcessor;
import com.jcute.core.error.BeanDefinitionMultipleException;
import com.jcute.core.error.BeanDefinitionNotFoundException;
import com.jcute.core.error.BeanDefinitionReferenceCycleException;
import com.jcute.core.error.BeanDefinitionSortException;
import com.jcute.core.error.BeanInstanceCreateException;
import com.jcute.core.toolkit.DirectedGraph;
import com.jcute.core.toolkit.DirectedGraphUtil;
import com.jcute.core.util.ReflectionUtil;
import com.jcute.core.util.StringUtil;

public abstract class AbstractBeanFactoryProcessor implements BeanFactoryProcessor{

	private final BeanFactory beanFactory;

	public AbstractBeanFactoryProcessor(BeanFactory beanFactory){
		if(null == beanFactory){
			throw new IllegalArgumentException("Bean factory must not be null");
		}
		this.beanFactory = beanFactory;
	}

	@Override
	public BeanFactory getBeanFactory(){
		return this.beanFactory;
	}

	@Override
	public Constructor<?> getBeanConstructor(Class<?> beanType){
		if(null == beanType){
			return null;
		}
		Constructor<?>[] constructors = beanType.getConstructors();
		if(null == constructors || constructors.length == 0){
			return null;
		}
		return this.getConstructor(constructors);
	}
	
	@Override
	public Object[] getConstructorArguments(Constructor<?> constructor) throws BeanDefinitionNotFoundException,BeanDefinitionMultipleException{
		Object[] arguments = this.getArguments(constructor.getParameterTypes(),constructor.getParameterAnnotations());
		if(null == arguments){
			return new Object[0];
		}
		return arguments;
	}

	@Override
	public Object[] getMethodArguments(Method method) throws BeanDefinitionNotFoundException,BeanDefinitionMultipleException{
		Object[] arguments = this.getArguments(method.getParameterTypes(),method.getParameterAnnotations());
		if(null == arguments){
			return new Object[0];
		}
		return arguments;
	}

	@Override
	public boolean isComponent(Class<?> beanType){
		if(null == beanType){
			return false;
		}
		if(beanType.isInterface() || Modifier.isAbstract(beanType.getModifiers())){
			return false;
		}
		return this.isComponentClass(beanType);
	}

	@Override
	public void invokeInitialMethods(Object beanInstance){
		if(null == beanInstance){
			return;
		}
		Method[] methods = this.getInitialMethods(beanInstance);
		if(null == methods || methods.length == 0){
			return;
		}
		try{
			for(int i = 0;i < methods.length;i++){
				Method method = methods[i];
				Object[] arguments = this.getMethodArguments(method);
				ReflectionUtil.makeAccessible(method);
				ReflectionUtil.invokeMethod(method,beanInstance,arguments);
			}
		}catch(Throwable t){
			t.printStackTrace();
		}
	}

	@Override
	public void invokeDestoryMethods(Object beanInstance){
		if(null == beanInstance){
			return;
		}
		Method[] methods = this.getDestoryMethods(beanInstance);
		if(null == methods || methods.length == 0){
			return;
		}
		try{
			for(int i = 0;i < methods.length;i++){
				Method method = methods[i];
				Object[] arguments = this.getMethodArguments(method);
				ReflectionUtil.makeAccessible(method);
				ReflectionUtil.invokeMethod(method,beanInstance,arguments);
			}
		}catch(Throwable t){
			t.printStackTrace();
		}
	}

	@Override
	public Set<String> searchScanPattern(String root){
		if(StringUtil.isBlank(root)){
			return new LinkedHashSet<String>();
		}
		Set<String> result = this.doSearchScanPattern(root);
		if(null == result){
			result = new LinkedHashSet<String>();
		}
		return result;
	}

	@Override
	public BeanDefinition[] searchInterceptor(Class<?> beanType){
		if(null == beanType){
			return new BeanDefinition[0];
		}
		BeanDefinition[] result = this.doSearchInterceptor(beanType);
		if(null == result){
			return new BeanDefinition[0];
		}
		return result;
	}

	@Override
	public Set<BeanDefinition> getBeanDefinitionsSorted() throws BeanDefinitionReferenceCycleException,BeanDefinitionSortException{
		DirectedGraph<BeanDefinition> directedGraph = new DirectedGraph<BeanDefinition>();
		DirectedGraphUtil<BeanDefinition> directedGraphUtil = new DirectedGraphUtil<BeanDefinition>(directedGraph);
		try{
			for(Entry<String,BeanDefinition> entry : this.beanFactory.getAllBeanDefinitions().entrySet()){
				this.processDirectedGraph(entry.getValue(),directedGraph);
			}
		}catch(Exception e){
			throw new BeanDefinitionSortException(e);
		}
		if(directedGraphUtil.hasCycle()){
			throw new BeanDefinitionReferenceCycleException();
		}
		return directedGraphUtil.getSort();
	}

	/**
	 * 获取需要注入的参数数组
	 * 
	 * @param parameterTypes
	 * @param annotations
	 * @return 返回参数数组
	 * @throws BeanDefinitionNotFoundException
	 * @throws BeanDefinitionMultipleException
	 * @throws BeanInstanceCreateException
	 */
	protected abstract Object[] getArguments(Class<?>[] parameterTypes,Annotation[][] annotations) throws BeanDefinitionNotFoundException,BeanDefinitionMultipleException;

	/**
	 * 根据自定义逻辑选择出需要实例化所使用的构造函数
	 * 
	 * @param constructors
	 * @return 返回具体构造函数
	 */
	protected abstract Constructor<?> getConstructor(Constructor<?>[] constructors);

	/**
	 * 判断指定类型是否符合注入标准
	 * 
	 * @param beanType
	 * @return true为符合
	 */
	protected abstract boolean isComponentClass(Class<?> beanType);

	/**
	 * 获取初始化方法,包含排序
	 * 
	 * @param beanInstance
	 * @return
	 */
	protected abstract Method[] getInitialMethods(Object beanInstance);

	/**
	 * 获取销毁方法,包含排序
	 * 
	 * @param beanInstance
	 * @return
	 */
	protected abstract Method[] getDestoryMethods(Object beanInstance);

	/**
	 * 搜索可识别的包
	 * 
	 * @param root
	 * @return
	 */
	protected abstract Set<String> doSearchScanPattern(String root);

	/**
	 * 搜索拦截器定义数组
	 * 
	 * @param beanType
	 * @return
	 */
	protected abstract BeanDefinition[] doSearchInterceptor(Class<?> beanType);

	/**
	 * 分析Bean信息,并将分析结果添加到图中,方便进行循环检测
	 * 
	 * @param beanDefinition
	 * @param directedGraph
	 * @throws BeanDefinitionNotFoundException
	 * @throws BeanDefinitionMultipleException
	 */
	protected abstract void processDirectedGraph(BeanDefinition beanDefinition,DirectedGraph<BeanDefinition> directedGraph) throws BeanDefinitionNotFoundException,BeanDefinitionMultipleException;

}