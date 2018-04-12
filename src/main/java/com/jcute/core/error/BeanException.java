package com.jcute.core.error;

/**
 * Bean相关处理异常
 * 
 * @author koko
 *
 */
public class BeanException extends Exception{

	private static final long serialVersionUID = -6594170352378966106L;

	public BeanException(){
		super();
	}

	public BeanException(String message,Throwable cause){
		super(message,cause);
	}

	public BeanException(String message){
		super(message);
	}

	public BeanException(Throwable cause){
		super(cause);
	}

}