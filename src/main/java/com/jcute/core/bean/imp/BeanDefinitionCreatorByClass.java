package com.jcute.core.bean.imp;

import java.lang.reflect.Constructor;

import com.jcute.core.bean.BeanDefinition;
import com.jcute.core.bean.BeanFactoryProcessor;
import com.jcute.core.bean.abs.AbstractBeanDefinitionCreator;
import com.jcute.core.error.BeanInstanceCreateException;
import com.jcute.core.proxy.ProxyManager;
import com.jcute.core.util.ReflectionUtil;

/**
 * BeanCreator实现,通过指定的class创建Bean实例
 * 
 * @author koko
 *
 */
public class BeanDefinitionCreatorByClass extends AbstractBeanDefinitionCreator{

	public BeanDefinitionCreatorByClass(BeanDefinition beanDefinition){
		super(beanDefinition);
	}

	@Override
	protected Object doCreateBeanInstance() throws BeanInstanceCreateException{
		try{
			BeanDefinition beanDefinition = this.getBeanDefinition();
			Class<?> beanType = beanDefinition.getBeanType();
			BeanFactoryProcessor beanFactoryProcessor = beanDefinition.getBeanFactory().getBeanFactoryProcessor();
			BeanDefinition[] interceptors = beanFactoryProcessor.searchInterceptor(beanType);
			Constructor<?> constructor = beanFactoryProcessor.getBeanConstructor(beanType);
			Object[] arguments = beanFactoryProcessor.getConstructorArguments(constructor);
			if(null == interceptors || interceptors.length == 0){
				return ReflectionUtil.invokeConstructor(constructor,arguments);
			}else{
				return ProxyManager.createProxy(beanType,interceptors,constructor.getParameterTypes(),arguments);
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new BeanInstanceCreateException(e.getMessage());
		}
	}

}