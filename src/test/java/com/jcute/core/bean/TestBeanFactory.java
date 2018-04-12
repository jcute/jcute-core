package com.jcute.core.bean;

import org.junit.Test;

import com.jcute.core.bean.imp.BeanDefinitionByClass;
import com.jcute.core.bean.imp.BeanDefinitionByInstance;
import com.jcute.core.bean.imp.BeanFactoryByAnnotation;
import com.jcute.core.error.BeanDefinitionMultipleException;

public class TestBeanFactory{

	@Test
	public void testCreateBeanFactory() throws BeanDefinitionMultipleException{
		BeanFactory beanFactory = new BeanFactoryByAnnotation(null);
		BeanInfoEntity beanInfoEntity = new BeanInfoEntity();
		BeanDefinition beanDefinition = new BeanDefinitionByInstance(beanFactory,BeanInfoEntity.class,null,null,beanInfoEntity);
		beanFactory.addBeanDefinition(beanDefinition);
		System.out.println(beanFactory);
		System.out.println(beanFactory.getBeanFactoryProcessor());
		System.out.println(beanFactory.getAllBeanDefinitions());
	}

	@Test
	public void testCreateBeanInstance() throws Exception{
		BeanFactory beanFactory = new BeanFactoryByAnnotation(null);
		BeanDefinition sbeanDefinition = new BeanDefinitionByClass(beanFactory,SingletonBeanInfoEntity.class,null,null);
		BeanDefinition pbeanDefinition = new BeanDefinitionByClass(beanFactory,PrototypeBeanInfoEntity.class,null,BeanScope.Prototype);
		beanFactory.addBeanDefinition(sbeanDefinition);
		beanFactory.addBeanDefinition(pbeanDefinition);
		beanFactory.initial();
		beanFactory.create();
		beanFactory.inject();

		System.out.println(beanFactory.getAllBeanDefinitions());
		
		System.out.println(beanFactory.getBean(SingletonBeanInfoEntity.class));
		System.out.println(beanFactory.getBean(SingletonBeanInfoEntity.class));
		System.out.println(((SingletonBeanInfoEntity)beanFactory.getBean(SingletonBeanInfoEntity.class)).getName());
		System.out.println(beanFactory.getBean(SingletonBeanInfoEntity.class).equals(beanFactory.getBean(SingletonBeanInfoEntity.class)));

		System.out.println(beanFactory.getBean(PrototypeBeanInfoEntity.class));
		System.out.println(beanFactory.getBean(PrototypeBeanInfoEntity.class));
		System.out.println(beanFactory.getBean(PrototypeBeanInfoEntity.class).equals(beanFactory.getBean(PrototypeBeanInfoEntity.class)));

		beanFactory.destory();
	}

}