package com.jcute.core.error;

/**
 * 上下文注入异常
 * 
 * @author koko
 *
 */
public class ContextInjectException extends ContextException{

	private static final long serialVersionUID = -1589427524688663384L;

	public ContextInjectException(){
		super();
	}

	public ContextInjectException(String message,Throwable cause){
		super(message,cause);
	}

	public ContextInjectException(String message){
		super(message);
	}

	public ContextInjectException(Throwable cause){
		super(cause);
	}

}