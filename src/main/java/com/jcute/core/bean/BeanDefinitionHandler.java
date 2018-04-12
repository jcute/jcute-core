package com.jcute.core.bean;

import com.jcute.core.error.BeanInstanceNotFoundException;
import com.jcute.core.toolkit.Lifecycle;

/**
 * Bean定义句柄,用来实现单例和多例的控制
 * 
 * @author koko
 *
 */
public interface BeanDefinitionHandler extends Lifecycle{

	/**
	 * 获取Bean定义实例,无法获取则抛出异常
	 * 
	 * @return 返回Bean实例
	 * @throws BeanInstanceNotFoundException
	 */
	public Object getBeanInstance() throws BeanInstanceNotFoundException;

	/**
	 * 获取Bean实例创建器
	 * 
	 * @return 返回Bean实例创建器
	 */
	public BeanDefinitionCreator getBeanDefinitionCreator();

	/**
	 * 返回Bean定义对象
	 * 
	 * @return BeanDefinition持有对象
	 */
	public BeanDefinition getBeanDefinition();

}