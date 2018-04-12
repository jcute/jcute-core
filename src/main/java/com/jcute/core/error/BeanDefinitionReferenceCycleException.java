package com.jcute.core.error;

public class BeanDefinitionReferenceCycleException extends BeanException{

	private static final long serialVersionUID = 1L;

	public BeanDefinitionReferenceCycleException(){
		super();
	}

	public BeanDefinitionReferenceCycleException(String message,Throwable cause){
		super(message,cause);
	}

	public BeanDefinitionReferenceCycleException(String message){
		super(message);
	}

	public BeanDefinitionReferenceCycleException(Throwable cause){
		super(cause);
	}

}
