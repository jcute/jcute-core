package com.jcute.core.error;

/**
 * 上下文创建异常
 * 
 * @author koko
 *
 */
public class ContextCreateException extends ContextException{

	private static final long serialVersionUID = 1674363919603099499L;

	public ContextCreateException(){
		super();
	}

	public ContextCreateException(String message,Throwable cause){
		super(message,cause);
	}

	public ContextCreateException(String message){
		super(message);
	}

	public ContextCreateException(Throwable cause){
		super(cause);
	}

}