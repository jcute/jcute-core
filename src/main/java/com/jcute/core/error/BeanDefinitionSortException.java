package com.jcute.core.error;

public class BeanDefinitionSortException extends BeanException{

	private static final long serialVersionUID = 1L;

	public BeanDefinitionSortException(){
		super();
	}

	public BeanDefinitionSortException(String message,Throwable cause){
		super(message,cause);
	}

	public BeanDefinitionSortException(String message){
		super(message);
	}

	public BeanDefinitionSortException(Throwable cause){
		super(cause);
	}

}