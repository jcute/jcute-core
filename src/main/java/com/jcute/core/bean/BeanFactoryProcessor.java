package com.jcute.core.bean;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Set;

import com.jcute.core.error.BeanDefinitionMultipleException;
import com.jcute.core.error.BeanDefinitionNotFoundException;
import com.jcute.core.error.BeanDefinitionReferenceCycleException;
import com.jcute.core.error.BeanDefinitionSortException;

/**
 * Bean定义处理器,解析
 * 
 * @author koko
 *
 */
public interface BeanFactoryProcessor{

	/**
	 * 返回Bean工厂,方便调用
	 * 
	 * @return BeanFactory实例
	 */
	public BeanFactory getBeanFactory();

	/**
	 * 获取beanType的构造函数
	 * 
	 * @param beanType
	 * @return 可为空,接口类型
	 */
	public Constructor<?> getBeanConstructor(Class<?> beanType);

	/**
	 * 获取构造函数参数
	 * 
	 * @param constructor
	 * @return 返回构造函数参数数组,无参返回零长度数组
	 */
	public Object[] getConstructorArguments(Constructor<?> constructor) throws BeanDefinitionNotFoundException,BeanDefinitionMultipleException;

	/**
	 * 获取方法参数
	 * 
	 * @param method
	 * @return 返回方法参数数组,无参返回零长度数组
	 */
	public Object[] getMethodArguments(Method method) throws BeanDefinitionNotFoundException,BeanDefinitionMultipleException;

	/**
	 * 判断当前class是否为可注入的Bean
	 * 
	 * @param beanType
	 * @return
	 */
	public boolean isComponent(Class<?> beanType);

	/**
	 * 判断当前method是否为可注入的Bean
	 * 
	 * @param method
	 * @return
	 */
	public boolean isComponent(Method method);

	/**
	 * 判断指定类型是否为配置类
	 * 
	 * @param beanType
	 * @return true为配置类
	 */
	public boolean isConfiguration(Class<?> beanType);

	/**
	 * 为Bean实例注入数据
	 * 
	 * @param beanInstance
	 */
	public void injectBeanInstance(Object beanInstance);

	/**
	 * 调用初始化方法
	 * 
	 * @param beanInstance
	 */
	public void invokeInitialMethods(Object beanInstance);

	/**
	 * 调用销毁方法
	 * 
	 * @param beanInstance
	 */
	public void invokeDestoryMethods(Object beanInstance);

	/**
	 * 搜索系统识别的包目录
	 * 
	 * @param root
	 * @return
	 */
	public Set<String> searchScanPattern(String root);

	/**
	 * 搜索拦截器的Bean定义
	 * 
	 * @param beanType
	 *            可为父类
	 * @return 拦截器Bean定义数组,无则为空数组
	 */
	public BeanDefinition[] searchInterceptor(Class<?> beanType);

	/**
	 * 返回所有的BeanDefinition信息<br/>
	 * 并将BeanDefinition信息按照引用关系进行排序<br/>
	 * 排序前会检测beandefinition之间是否有环引用
	 * 
	 * @return 返回排序后的BeanDefinition
	 * @throws BeanDefinitionReferenceCycleException
	 * @throws BeanDefinitionSortException
	 */
	public Set<BeanDefinition> getBeanDefinitionsSorted() throws BeanDefinitionReferenceCycleException,BeanDefinitionSortException;

	/**
	 * Bean定义注册,快捷注册功能
	 * 
	 * @param target
	 */
	// public void addBeanDefinition(Class<?> target) throws BeanDefinitionMultipleException;

	/**
	 * 创建Bean定义对象
	 * 
	 * @param target
	 * @return
	 */
	public BeanDefinition createBeanDefinition(Class<?> target);

	/**
	 * 根据传入的method对象,分析出BeanName
	 * 
	 * @param method
	 * @return BeanName
	 */
	public String resolveBeanName(Method method);

	/**
	 * 根据传入的method对象,分析出BeanScope
	 * 
	 * @param method
	 * @return BeanScope
	 */
	public BeanScope resolveBeanScope(Method method);

}