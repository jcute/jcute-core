package com.jcute.core.bean.imp;

import com.jcute.core.bean.BeanDefinition;
import com.jcute.core.bean.BeanDefinitionCreator;
import com.jcute.core.bean.BeanFactoryProcessor;
import com.jcute.core.bean.abs.AbstractBeanDefinitionHandler;
import com.jcute.core.error.BeanInstanceCreateException;
import com.jcute.core.error.BeanInstanceNotFoundException;
import com.jcute.core.error.ContextCreateException;
import com.jcute.core.error.ContextDestoryException;
import com.jcute.core.error.ContextInitialException;
import com.jcute.core.error.ContextInjectException;

public class BeanDefinitionHandlerBySingleton extends AbstractBeanDefinitionHandler{

	private BeanFactoryProcessor beanFactoryProcessor;
	private Object beanInstance;

	public BeanDefinitionHandlerBySingleton(BeanDefinition beanDefinition,BeanDefinitionCreator beanDefinitionCreator){
		super(beanDefinition,beanDefinitionCreator);
	}

	@Override
	protected void doInitial() throws ContextInitialException{
		this.beanFactoryProcessor = this.getBeanDefinition().getBeanFactory().getBeanFactoryProcessor();
	}

	@Override
	protected void doCreate() throws ContextCreateException{
		try{
			this.beanInstance = this.getBeanDefinitionCreator().createBeanInstace();
		}catch(BeanInstanceCreateException e){
			throw new ContextCreateException(e.getMessage());
		}
	}

	@Override
	protected void doInject() throws ContextInjectException{
		this.beanFactoryProcessor.injectBeanInstance(this.beanInstance);
		this.beanFactoryProcessor.invokeInitialMethods(this.beanInstance);
	}

	@Override
	protected void doDestory() throws ContextDestoryException{
		this.beanFactoryProcessor.invokeDestoryMethods(this.beanInstance);
		this.beanInstance = null;
	}

	@Override
	protected Object doGetBeanInstance() throws BeanInstanceNotFoundException{
		return this.beanInstance;
	}

}