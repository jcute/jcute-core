package com.jcute.core.bean;

import com.jcute.core.error.BeanInstanceNotFoundException;
import com.jcute.core.toolkit.Lifecycle;

/**
 * Bean定义接口规范<br/>
 * <ul>
 * <li>定义Bean类型</li>
 * <li>定义Bean名称</li>
 * <li>定义Bean作用域</li>
 * <li>定义Bean拦截器</li>
 * </ul>
 * 
 * @author koko
 *
 */
public interface BeanDefinition extends Lifecycle{

	/**
	 * 返回Bean工厂
	 * 
	 * @return
	 */
	public BeanFactory getBeanFactory();

	/**
	 * 获取Bean定义目标类型,只读属性
	 * 
	 * @return 返回Bean类型
	 */
	public Class<?> getBeanType();

	/**
	 * 获取Bean定义名称,只读属性
	 * 
	 * @return 返回Bean名称
	 */
	public String getBeanName();

	/**
	 * 获取Bean定义作用域,只读属性
	 * 
	 * @return 返回Bean作用域
	 */
	public BeanScope getBeanScope();

	/**
	 * 判断当前Bean定义是否为单例模式,快捷方法,根据BeanScope做判断
	 * 
	 * @return true为单例
	 */
	public boolean isSingleton();

	/**
	 * 判断当前Bean定义是否为多例模式,快捷方法,根据BeanScope做判断
	 * 
	 * @return true为多例
	 */
	public boolean isPrototype();

	/**
	 * 判断传入的类是否与当前Bean定义的目标类匹配,采用isAssignableFrom判断,即父类或当前类都可以
	 * 
	 * @param target
	 * @return true为匹配
	 */
	public boolean isAssignable(Class<?> target);

	/**
	 * 获取Bean定的实例,如果不存在抛出异常
	 * 
	 * @return 返回Bean实例
	 * @throws BeanInstanceNotFoundException
	 */
	public Object getBeanInstance() throws BeanInstanceNotFoundException;

	/**
	 * 获取Bean定义的创建器
	 * 
	 * @return 返回创建器实例
	 */
	public BeanDefinitionCreator getBeanDefinitionCreator();

	/**
	 * 获取Bean定义的实例句柄
	 * 
	 * @return 返回实例句柄对象
	 */
	public BeanDefinitionHandler getBeanDefinitionHandler();

}