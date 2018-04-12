package com.jcute.core.bean;

import com.jcute.core.error.BeanInstanceCreateException;

/**
 * Bean实例创建器,只负责创建Bean实例,不关心单例或多例模式
 * 
 * @author koko
 *
 */
public interface BeanDefinitionCreator{

	/**
	 * 负责创建Bean实例,创建失败抛出异常
	 * 
	 * @return 返回Bean实例
	 * @throws BeanInstanceCreateException
	 */
	public Object createBeanInstace() throws BeanInstanceCreateException;

	/**
	 * 返回Bean定义对象
	 * 
	 * @return BeanDefinition持有对象
	 */
	public BeanDefinition getBeanDefinition();

}