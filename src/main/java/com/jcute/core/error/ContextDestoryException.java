package com.jcute.core.error;

/**
 * 上下文销毁异常
 * 
 * @author koko
 *
 */
public class ContextDestoryException extends ContextException{

	private static final long serialVersionUID = 3248685781773400420L;

	public ContextDestoryException(){
		super();
	}

	public ContextDestoryException(String message,Throwable cause){
		super(message,cause);
	}

	public ContextDestoryException(String message){
		super(message);
	}

	public ContextDestoryException(Throwable cause){
		super(cause);
	}

}