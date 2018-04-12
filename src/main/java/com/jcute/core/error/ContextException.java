package com.jcute.core.error;

public class ContextException extends Exception{

	private static final long serialVersionUID = -6790757353442217111L;

	public ContextException(){
		super();
	}

	public ContextException(String message,Throwable cause){
		super(message,cause);
	}

	public ContextException(String message){
		super(message);
	}

	public ContextException(Throwable cause){
		super(cause);
	}

}