package com.jcute.core.bean.imp;

import java.lang.reflect.Method;

import com.jcute.core.bean.BeanDefinition;
import com.jcute.core.bean.BeanDefinitionCreator;
import com.jcute.core.bean.BeanDefinitionHandler;
import com.jcute.core.bean.BeanFactory;
import com.jcute.core.bean.BeanScope;
import com.jcute.core.bean.abs.AbstractBeanDefinition;

public class BeanDefinitionByMethod extends AbstractBeanDefinition{

	private final BeanDefinitionCreator beanDefinitionCreator;
	private final BeanDefinitionHandler beanDefinitionHandler;

	private final BeanDefinition parentBeanDefinition;
	private final Method beanCreateMethod;

	public BeanDefinitionByMethod(BeanFactory beanFactory,Class<?> beanType,String beanName,BeanScope beanScope,BeanDefinition parentBeanDefinition,Method beanCreateMethod){
		super(beanFactory,beanType,beanName,beanScope);
		if(null == parentBeanDefinition){
			throw new IllegalArgumentException("Parent bean definition must not be null");
		}
		if(null == beanCreateMethod){
			throw new IllegalArgumentException("Bean create method must not be null");
		}
		this.parentBeanDefinition = parentBeanDefinition;
		this.beanCreateMethod = beanCreateMethod;
		this.beanDefinitionCreator = new BeanDefinitionCreatorByMethod(this,parentBeanDefinition,beanCreateMethod);
		if(this.isSingleton()){
			this.beanDefinitionHandler = new BeanDefinitionHandlerBySingleton(this,this.beanDefinitionCreator);
		}else{
			this.beanDefinitionHandler = new BeanDefinitionHandlerByPrototype(this,this.beanDefinitionCreator);
		}
	}

	public final BeanDefinition getParentBeanDefinition(){
		return this.parentBeanDefinition;
	}

	public final Method getBeanCreateMethod(){
		return this.beanCreateMethod;
	}
	
	@Override
	public BeanDefinitionCreator getBeanDefinitionCreator(){
		return this.beanDefinitionCreator;
	}

	@Override
	public BeanDefinitionHandler getBeanDefinitionHandler(){
		return this.beanDefinitionHandler;
	}

}