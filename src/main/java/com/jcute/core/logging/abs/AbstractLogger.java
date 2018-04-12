package com.jcute.core.logging.abs;

import java.io.Serializable;

import com.jcute.core.logging.Logger;
import com.jcute.core.logging.LoggerLevel;
import com.jcute.core.util.StringUtil;

public abstract class AbstractLogger implements Logger, Serializable{

	private static final long serialVersionUID = 1L;

	private static final String EXCEPTION_MESSAGE = "unexpected exception:";

	private final String name;

	public AbstractLogger(String name){
		if(StringUtil.isBlank(name)){
			throw new NullPointerException("name");
		}
		this.name = name;
	}

	@Override
	public String getName(){
		return this.name;
	}

	@Override
	public boolean isEnabled(LoggerLevel loggerLevel){
		switch(loggerLevel){
		case TRACE:
			return isTraceEnabled();
		case DEBUG:
			return isDebugEnabled();
		case INFO:
			return isInfoEnabled();
		case WARN:
			return isWarnEnabled();
		case ERROR:
			return isErrorEnabled();
		default:
			throw new Error();
		}
	}

	@Override
	public void trace(Throwable t){
		this.trace(EXCEPTION_MESSAGE,t);
	}

	@Override
	public void debug(Throwable t){
		this.debug(EXCEPTION_MESSAGE,t);
	}

	@Override
	public void info(Throwable t){
		this.info(EXCEPTION_MESSAGE,t);
	}

	@Override
	public void warn(Throwable t){
		this.warn(EXCEPTION_MESSAGE,t);
	}

	@Override
	public void error(Throwable t){
		this.error(EXCEPTION_MESSAGE,t);
	}

	@Override
	public void log(LoggerLevel level,String msg){
		switch(level){
		case TRACE:
			this.trace(msg);
		case DEBUG:
			this.debug(msg);
		case INFO:
			this.info(msg);
		case WARN:
			this.warn(msg);
		case ERROR:
			this.error(msg);
		default:
			throw new Error();
		}
	}

	@Override
	public void log(LoggerLevel level,Throwable t){
		switch(level){
		case TRACE:
			this.trace(t);
		case DEBUG:
			this.debug(t);
		case INFO:
			this.info(t);
		case WARN:
			this.warn(t);
		case ERROR:
			this.error(t);
		default:
			throw new Error();
		}
	}

	@Override
	public void log(LoggerLevel level,String msg,Throwable t){
		switch(level){
		case TRACE:
			this.trace(msg,t);
		case DEBUG:
			this.debug(msg,t);
		case INFO:
			this.info(msg,t);
		case WARN:
			this.warn(msg,t);
		case ERROR:
			this.error(msg,t);
		default:
			throw new Error();
		}
	}

	@Override
	public void log(LoggerLevel level,String format,Object arg){
		switch(level){
		case TRACE:
			this.trace(format,arg);
		case DEBUG:
			this.debug(format,arg);
		case INFO:
			this.info(format,arg);
		case WARN:
			this.warn(format,arg);
		case ERROR:
			this.error(format,arg);
		default:
			throw new Error();
		}
	}

	@Override
	public void log(LoggerLevel level,String format,Object argA,Object argB){
		switch(level){
		case TRACE:
			this.trace(format,argA,argB);
		case DEBUG:
			this.debug(format,argA,argB);
		case INFO:
			this.info(format,argA,argB);
		case WARN:
			this.warn(format,argA,argB);
		case ERROR:
			this.error(format,argA,argB);
		default:
			throw new Error();
		}
	}

	@Override
	public void log(LoggerLevel level,String format,Object... arguments){
		switch(level){
		case TRACE:
			this.trace(format,arguments);
		case DEBUG:
			this.debug(format,arguments);
		case INFO:
			this.info(format,arguments);
		case WARN:
			this.warn(format,arguments);
		case ERROR:
			this.error(format,arguments);
		default:
			throw new Error();
		}
	}

	@Override
	public String toString(){
		return String.format("%s(%s)",this.getClass().getSimpleName(),this.getName());
	}

}