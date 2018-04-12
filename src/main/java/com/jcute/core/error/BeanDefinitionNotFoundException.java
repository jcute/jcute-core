package com.jcute.core.error;

public class BeanDefinitionNotFoundException extends BeanException{

	private static final long serialVersionUID = 1L;

	public BeanDefinitionNotFoundException(){
		super();
	}

	public BeanDefinitionNotFoundException(String message,Throwable cause){
		super(message,cause);
	}

	public BeanDefinitionNotFoundException(String message){
		super(message);
	}

	public BeanDefinitionNotFoundException(Throwable cause){
		super(cause);
	}

}