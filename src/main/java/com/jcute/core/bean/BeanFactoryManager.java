package com.jcute.core.bean;

import java.util.Map;

import com.jcute.core.error.BeanDefinitionMultipleException;
import com.jcute.core.error.BeanDefinitionNotFoundException;

/**
 * 单独抽象出Bean管理功能
 * 
 * @author koko
 *
 */
public interface BeanFactoryManager{

	/**
	 * 判断是否存在指定的Bean类型
	 * 
	 * @param beanType
	 * @return true为存在
	 */
	public boolean containsBean(Class<?> beanType);

	/**
	 * 判断是否存在指定的Bean类型
	 * 
	 * @param beanName
	 * @return true为存在
	 */
	public boolean containsBean(String beanName);

	/**
	 * 判断指定类型和名称的Bean是否存在
	 * 
	 * @param beanType
	 * @param beanName
	 * @return true为存在
	 */
	public boolean containsBean(Class<?> beanType,String beanName);

	/**
	 * 获取Bean实例对象
	 * 
	 * @param beanType
	 * @return 返回实例
	 * @throws BeanDefinitionNotFoundException
	 *             Bean对象不存在
	 * @throws BeanDefinitionMultipleException
	 *             Bean对象存在多个,无法确定返回哪一个
	 */
	public Object getBean(Class<?> beanType) throws BeanDefinitionNotFoundException,BeanDefinitionMultipleException;

	/**
	 * 获取Bean实例对象
	 * 
	 * @param beanName
	 * @return 返回实例
	 * @throws BeanDefinitionNotFoundException
	 * @throws BeanDefinitionMultipleException
	 */
	public Object getBean(String beanName) throws BeanDefinitionNotFoundException,BeanDefinitionMultipleException;

	/**
	 * 获取Bean实例对象
	 * 
	 * @param beanType
	 * @param beanName
	 * @return 返回实例
	 * @throws BeanDefinitionNotFoundException
	 * @throws BeanDefinitionMultipleException
	 */
	public Object getBean(Class<?> beanType,String beanName) throws BeanDefinitionNotFoundException,BeanDefinitionMultipleException;

	/**
	 * 获取Bean定义
	 * 
	 * @param beanType
	 * @return 返回Bean定义对象
	 * @throws BeanDefinitionNotFoundException
	 * @throws BeanDefinitionMultipleException
	 */
	public BeanDefinition getBeanDefinition(Class<?> beanType) throws BeanDefinitionNotFoundException,BeanDefinitionMultipleException;

	/**
	 * 获取Bean定义
	 * 
	 * @param beanName
	 * @return 返回Bean定义对象
	 * @throws BeanDefinitionNotFoundException
	 * @throws BeanDefinitionMultipleException
	 */
	public BeanDefinition getBeanDefinition(String beanName) throws BeanDefinitionNotFoundException,BeanDefinitionMultipleException;

	/**
	 * 获取Bean定义
	 * 
	 * @param beanType
	 * @param beanName
	 * @return 返回Bean定义对象
	 * @throws BeanDefinitionNotFoundException
	 * @throws BeanDefinitionMultipleException
	 */
	public BeanDefinition getBeanDefinition(Class<?> beanType,String beanName) throws BeanDefinitionNotFoundException,BeanDefinitionMultipleException;

	/**
	 * 获取指定类型的实例集合
	 * 
	 * @param beanType
	 * @return 返回具体定义
	 * @throws BeanDefinitionNotFoundException
	 */
	public Map<String,Object> getBeans(Class<?> beanType) throws BeanDefinitionNotFoundException;

	/**
	 * 获取指定类型的bean定义集合<br/>
	 * 如果BeanType为空，则返回全部的Bean定义信息
	 * 
	 * @param beanType
	 * @return 返回bean定义即可,beanType为null,返回所有
	 * @throws BeanDefinitionNotFoundException
	 */
	public Map<String,BeanDefinition> getBeanDefinitions(Class<?> beanType) throws BeanDefinitionNotFoundException;

	/**
	 * 获取所有的Bean定义
	 * 
	 * @return
	 */
	public Map<String,BeanDefinition> getAllBeanDefinitions();

	/**
	 * 注册Bean定义
	 * 
	 * @param beanDefinition
	 */
	public void addBeanDefinition(BeanDefinition beanDefinition) throws BeanDefinitionMultipleException;
	
	/**
	 * 注册Bean定义
	 * @param target
	 * @throws BeanDefinitionMultipleException
	 */
	public void addBeanDefinition(Class<?> target) throws BeanDefinitionMultipleException;

	/**
	 * 注册Bean定义，只搜索method
	 * 
	 * @param target
	 * @throws BeanDefinitionMultipleException
	 */
	public void addMethodBeanDefinition(Class<?> target) throws BeanDefinitionMultipleException;

}