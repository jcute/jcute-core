package com.jcute.core.bean.imp;

import com.jcute.core.bean.BeanDefinitionCreator;
import com.jcute.core.bean.BeanDefinitionHandler;
import com.jcute.core.bean.BeanFactory;
import com.jcute.core.bean.BeanScope;
import com.jcute.core.bean.abs.AbstractBeanDefinition;

public class BeanDefinitionByClass extends AbstractBeanDefinition{

	private final BeanDefinitionCreator beanDefinitionCreator;
	private final BeanDefinitionHandler beanDefinitionHandler;

	public BeanDefinitionByClass(BeanFactory beanFactory,Class<?> beanType,String beanName,BeanScope beanScope){
		super(beanFactory,beanType,beanName,beanScope);
		this.beanDefinitionCreator = new BeanDefinitionCreatorByClass(this);
		if(this.isSingleton()){
			this.beanDefinitionHandler = new BeanDefinitionHandlerBySingleton(this,this.beanDefinitionCreator);
		}else{
			this.beanDefinitionHandler = new BeanDefinitionHandlerByPrototype(this,this.beanDefinitionCreator);
		}
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