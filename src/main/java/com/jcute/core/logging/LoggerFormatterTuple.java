package com.jcute.core.logging;

/**
 * 日志格式化临时存储对象
 */
public class LoggerFormatterTuple{

	private final String message;
	private final Throwable throwable;

	public LoggerFormatterTuple(String message,Throwable throwable){
		this.message = message;
		this.throwable = throwable;
	}

	public String getMessage(){
		return message;
	}

	public Throwable getThrowable(){
		return throwable;
	}

}