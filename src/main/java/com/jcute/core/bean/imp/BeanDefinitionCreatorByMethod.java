package com.jcute.core.bean.imp;

import java.lang.reflect.Method;

import com.jcute.core.bean.BeanDefinition;
import com.jcute.core.bean.BeanFactoryProcessor;
import com.jcute.core.bean.abs.AbstractBeanDefinitionCreator;
import com.jcute.core.error.BeanInstanceCreateException;
import com.jcute.core.proxy.ProxyManager;
import com.jcute.core.util.ReflectionUtil;

/**
 * BeanCreator实现,通过指定的method创建Bean实例
 * 
 * @author koko
 *
 */
public class BeanDefinitionCreatorByMethod extends AbstractBeanDefinitionCreator{

	private final BeanDefinition parentBeanDefinition;
	private final Method beanCreateMethod;

	/**
	 * 在构造函数中需要指定method对相Bean定义,后续创建需要使用,Method也必须传入,需要在创建时通过反射调用
	 * 
	 * @param beanDefinition
	 * @param parentBeanDefinition
	 * @param beanCreateMethod
	 */
	public BeanDefinitionCreatorByMethod(BeanDefinition beanDefinition,BeanDefinition parentBeanDefinition,Method beanCreateMethod){
		super(beanDefinition);
		if(null == parentBeanDefinition){
			throw new IllegalArgumentException("Parent bean definition must not be null");
		}
		if(null == beanCreateMethod){
			throw new IllegalArgumentException("Bean create method must not be null");
		}
		this.parentBeanDefinition = parentBeanDefinition;
		this.beanCreateMethod = beanCreateMethod;
	}

	public final BeanDefinition getParentBeanDefinition(){
		return this.parentBeanDefinition;
	}

	public final Method getBeanCreateMethod(){
		return this.beanCreateMethod;
	}

	@Override
	protected Object doCreateBeanInstance() throws BeanInstanceCreateException{
		try{

			BeanDefinition beanDefinition = this.getBeanDefinition();
			Class<?> beanType = beanDefinition.getBeanType();
			BeanFactoryProcessor beanFactoryProcessor = beanDefinition.getBeanFactory().getBeanFactoryProcessor();
			BeanDefinition[] interceptors = beanFactoryProcessor.searchInterceptor(beanType);
			if(null == interceptors || interceptors.length == 0){
				Object[] arguments = beanFactoryProcessor.getMethodArguments(this.beanCreateMethod);
				ReflectionUtil.makeAccessible(this.beanCreateMethod);
				return ReflectionUtil.invokeMethod(this.beanCreateMethod,this.parentBeanDefinition.getBeanInstance(),arguments);
			}else{
				return ProxyManager.createProxy(beanType,interceptors);
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new BeanInstanceCreateException(e.getMessage());
		}
	}

}