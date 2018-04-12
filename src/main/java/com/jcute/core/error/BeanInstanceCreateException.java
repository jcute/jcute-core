package com.jcute.core.error;

public class BeanInstanceCreateException extends BeanException{

	private static final long serialVersionUID = -9210666503075054494L;

	public BeanInstanceCreateException(){
		super();
	}

	public BeanInstanceCreateException(String message,Throwable cause){
		super(message,cause);
	}

	public BeanInstanceCreateException(String message){
		super(message);
	}

	public BeanInstanceCreateException(Throwable cause){
		super(cause);
	}

}