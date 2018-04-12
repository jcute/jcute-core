package com.jcute.core.error;

/**
 * 上下文初始化异常
 * 
 * @author koko
 *
 */
public class ContextInitialException extends ContextException{

	private static final long serialVersionUID = -62441342960907644L;

	public ContextInitialException(){
		super();
	}

	public ContextInitialException(String message,Throwable cause){
		super(message,cause);
	}

	public ContextInitialException(String message){
		super(message);
	}

	public ContextInitialException(Throwable cause){
		super(cause);
	}

}