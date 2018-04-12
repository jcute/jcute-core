package com.jcute.core.context;

import java.util.Map;

import com.jcute.core.bean.BeanFactory;
import com.jcute.core.error.ContextDestoryException;
import com.jcute.core.error.ContextInitialException;

/**
 * JCute上下文
 * 
 * @author tangbin
 *
 */
public interface ApplicationContext{

	/**
	 * 初始化
	 * 
	 * @throws ContextInitialException
	 */
	public void initial() throws ContextInitialException;

	/**
	 * 销毁
	 * 
	 * @throws ContextDestoryException
	 */
	public void destory() throws ContextDestoryException;

	/**
	 * 返回指定类型的Bean对象，如果当前类型存在多个实例或子类实例，则返回null
	 * 
	 * @param beanType
	 * @return 可为null
	 */
	public <T> T getBean(Class<T> beanType);

	/**
	 * 返回指定名称的实例对象
	 * 
	 * @param beanName
	 * @return 可为null
	 */
	public <T> T getBean(String beanName);

	/**
	 * 返回指定类型和名称的实例
	 * 
	 * @param beanType
	 * @param beanName
	 * @return 可为null
	 */
	public <T> T getBean(Class<T> beanType,String beanName);

	/**
	 * 返回指定类型的实例集合
	 * 
	 * @param beanType
	 * @return 不存在返回null
	 */
	public <T> Map<String,T> getBeans(Class<T> beanType);

	/**
	 * 获取Bean工厂
	 * 
	 * @return
	 */
	public BeanFactory getBeanFactory();

}