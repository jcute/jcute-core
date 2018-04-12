package com.jcute.core.error;

/**
 * Bean实例获取失败异常
 * 
 * @author koko
 *
 */
public class BeanInstanceNotFoundException extends BeanException{

	private static final long serialVersionUID = -5363583611942971684L;

	public BeanInstanceNotFoundException(){
		super();
	}

	public BeanInstanceNotFoundException(String message,Throwable cause){
		super(message,cause);
	}

	public BeanInstanceNotFoundException(String message){
		super(message);
	}

	public BeanInstanceNotFoundException(Throwable cause){
		super(cause);
	}

}