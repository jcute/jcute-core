package com.jcute.core.logging.imp;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.jcute.core.logging.LoggerFormatter;
import com.jcute.core.logging.LoggerFormatterTuple;
import com.jcute.core.logging.abs.AbstractLogger;

/**
 * log4j日志功能封装
 */
public class LoggerForLOG4J extends AbstractLogger{

	private static final long serialVersionUID = 1L;
	private static final String FQCN = LoggerForLOG4J.class.getName();

	private final transient Logger logger;
	private final boolean traceCapable;

	public LoggerForLOG4J(Logger logger){
		super(logger.getName());
		this.logger = logger;
		this.traceCapable = isTraceCapable();
	}

	private boolean isTraceCapable(){
		try{
			this.logger.isTraceEnabled();
			return true;
		}catch(NoSuchMethodError e){
			return false;
		}
	}

	@Override
	public boolean isTraceEnabled(){
		if(this.traceCapable){
			return this.logger.isTraceEnabled();
		}else{
			return this.logger.isDebugEnabled();
		}
	}

	@Override
	public boolean isDebugEnabled(){
		return this.logger.isDebugEnabled();
	}

	@Override
	public boolean isInfoEnabled(){
		return this.logger.isInfoEnabled();
	}

	@Override
	public boolean isWarnEnabled(){
		return this.logger.isEnabledFor(Level.WARN);
	}

	@Override
	public boolean isErrorEnabled(){
		return this.logger.isEnabledFor(Level.ERROR);
	}

	@Override
	public void trace(String msg){
		this.logger.log(FQCN,traceCapable ? Level.TRACE : Level.DEBUG,msg,null);
	}

	@Override
	public void trace(String msg,Throwable t){
		this.logger.log(FQCN,traceCapable ? Level.TRACE : Level.DEBUG,msg,t);
	}

	@Override
	public void trace(String format,Object arg){
		if(this.isTraceEnabled()){
			LoggerFormatterTuple ft = LoggerFormatter.format(format,arg);
			this.logger.log(FQCN,traceCapable ? Level.TRACE : Level.DEBUG,ft.getMessage(),ft.getThrowable());
		}
	}

	@Override
	public void trace(String format,Object argA,Object argB){
		if(this.isTraceEnabled()){
			LoggerFormatterTuple ft = LoggerFormatter.format(format,argA,argB);
			this.logger.log(FQCN,traceCapable ? Level.TRACE : Level.DEBUG,ft.getMessage(),ft.getThrowable());
		}
	}

	@Override
	public void trace(String format,Object... arguments){
		if(this.isTraceEnabled()){
			LoggerFormatterTuple ft = LoggerFormatter.arrayFormat(format,arguments);
			this.logger.log(FQCN,traceCapable ? Level.TRACE : Level.DEBUG,ft.getMessage(),ft.getThrowable());
		}
	}

	@Override
	public void debug(String msg){
		this.logger.log(FQCN,Level.DEBUG,msg,null);
	}

	@Override
	public void debug(String msg,Throwable t){
		this.logger.log(FQCN,Level.DEBUG,msg,t);
	}

	@Override
	public void debug(String format,Object arg){
		if(this.logger.isDebugEnabled()){
			LoggerFormatterTuple ft = LoggerFormatter.format(format,arg);
			this.logger.log(FQCN,Level.DEBUG,ft.getMessage(),ft.getThrowable());
		}
	}

	@Override
	public void debug(String format,Object argA,Object argB){
		if(this.logger.isDebugEnabled()){
			LoggerFormatterTuple ft = LoggerFormatter.format(format,argA,argB);
			this.logger.log(FQCN,Level.DEBUG,ft.getMessage(),ft.getThrowable());
		}
	}

	@Override
	public void debug(String format,Object... arguments){
		if(this.logger.isDebugEnabled()){
			LoggerFormatterTuple ft = LoggerFormatter.arrayFormat(format,arguments);
			this.logger.log(FQCN,Level.DEBUG,ft.getMessage(),ft.getThrowable());
		}
	}

	@Override
	public void info(String msg){
		this.logger.log(FQCN,Level.INFO,msg,null);
	}

	@Override
	public void info(String msg,Throwable t){
		this.logger.log(FQCN,Level.INFO,msg,t);
	}

	@Override
	public void info(String format,Object arg){
		if(this.logger.isInfoEnabled()){
			LoggerFormatterTuple ft = LoggerFormatter.format(format,arg);
			this.logger.log(FQCN,Level.INFO,ft.getMessage(),ft.getThrowable());
		}
	}

	@Override
	public void info(String format,Object argA,Object argB){
		if(this.logger.isInfoEnabled()){
			LoggerFormatterTuple ft = LoggerFormatter.format(format,argA,argB);
			this.logger.log(FQCN,Level.INFO,ft.getMessage(),ft.getThrowable());
		}
	}

	@Override
	public void info(String format,Object... arguments){
		if(this.logger.isInfoEnabled()){
			LoggerFormatterTuple ft = LoggerFormatter.arrayFormat(format,arguments);
			this.logger.log(FQCN,Level.INFO,ft.getMessage(),ft.getThrowable());
		}
	}

	@Override
	public void warn(String msg){
		this.logger.log(FQCN,Level.WARN,msg,null);
	}

	@Override
	public void warn(String msg,Throwable t){
		this.logger.log(FQCN,Level.WARN,msg,t);
	}

	@Override
	public void warn(String format,Object arg){
		if(this.logger.isEnabledFor(Level.WARN)){
			LoggerFormatterTuple ft = LoggerFormatter.format(format,arg);
			this.logger.log(FQCN,Level.WARN,ft.getMessage(),ft.getThrowable());
		}
	}

	@Override
	public void warn(String format,Object argA,Object argB){
		if(this.logger.isEnabledFor(Level.WARN)){
			LoggerFormatterTuple ft = LoggerFormatter.format(format,argA,argB);
			this.logger.log(FQCN,Level.WARN,ft.getMessage(),ft.getThrowable());
		}
	}

	@Override
	public void warn(String format,Object... arguments){
		if(logger.isEnabledFor(Level.WARN)){
			LoggerFormatterTuple ft = LoggerFormatter.arrayFormat(format,arguments);
			this.logger.log(FQCN,Level.WARN,ft.getMessage(),ft.getThrowable());
		}
	}

	@Override
	public void error(String msg){
		this.logger.log(FQCN,Level.ERROR,msg,null);
	}

	@Override
	public void error(String msg,Throwable t){
		this.logger.log(FQCN,Level.ERROR,msg,t);
	}

	@Override
	public void error(String format,Object arg){
		if(logger.isEnabledFor(Level.ERROR)){
			LoggerFormatterTuple ft = LoggerFormatter.format(format,arg);
			this.logger.log(FQCN,Level.ERROR,ft.getMessage(),ft.getThrowable());
		}
	}

	@Override
	public void error(String format,Object argA,Object argB){
		if(this.logger.isEnabledFor(Level.ERROR)){
			LoggerFormatterTuple ft = LoggerFormatter.format(format,argA,argB);
			this.logger.log(FQCN,Level.ERROR,ft.getMessage(),ft.getThrowable());
		}
	}

	@Override
	public void error(String format,Object... arguments){
		if(this.logger.isEnabledFor(Level.ERROR)){
			LoggerFormatterTuple ft = LoggerFormatter.arrayFormat(format,arguments);
			this.logger.log(FQCN,Level.ERROR,ft.getMessage(),ft.getThrowable());
		}
	}

}