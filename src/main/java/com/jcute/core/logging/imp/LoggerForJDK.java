package com.jcute.core.logging.imp;

import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import com.jcute.core.logging.LoggerFormatter;
import com.jcute.core.logging.LoggerFormatterTuple;
import com.jcute.core.logging.abs.AbstractLogger;

/**
 * JDK自带日志功能封装
 */
public class LoggerForJDK extends AbstractLogger{

	private static final long serialVersionUID = 1L;
	private static final String SELF = LoggerForJDK.class.getName();
	private static final String SUPER = AbstractLogger.class.getName();

	private final transient Logger logger;

	public LoggerForJDK(Logger logger){
		super(logger.getName());
		this.logger = logger;
	}

	@Override
	public boolean isTraceEnabled(){
		return this.logger.isLoggable(Level.FINEST);
	}

	@Override
	public boolean isDebugEnabled(){
		return this.logger.isLoggable(Level.FINE);
	}

	@Override
	public boolean isInfoEnabled(){
		return this.logger.isLoggable(Level.INFO);
	}

	@Override
	public boolean isWarnEnabled(){
		return this.logger.isLoggable(Level.WARNING);
	}

	@Override
	public boolean isErrorEnabled(){
		return this.logger.isLoggable(Level.SEVERE);
	}

	@Override
	public void trace(String msg){
		if(this.logger.isLoggable(Level.FINEST)){
			this.log(SELF,Level.FINEST,msg,null);
		}
	}

	@Override
	public void trace(String msg,Throwable t){
		if(this.logger.isLoggable(Level.FINEST)){
			this.log(SELF,Level.FINEST,msg,t);
		}
	}

	@Override
	public void trace(String format,Object arg){
		if(this.logger.isLoggable(Level.FINEST)){
			LoggerFormatterTuple ft = LoggerFormatter.format(format,arg);
			this.log(SELF,Level.FINEST,ft.getMessage(),ft.getThrowable());
		}
	}

	@Override
	public void trace(String format,Object argA,Object argB){
		if(this.logger.isLoggable(Level.FINEST)){
			LoggerFormatterTuple ft = LoggerFormatter.format(format,argA,argB);
			this.log(SELF,Level.FINEST,ft.getMessage(),ft.getThrowable());
		}
	}

	@Override
	public void trace(String format,Object... arguments){
		if(this.logger.isLoggable(Level.FINEST)){
			LoggerFormatterTuple ft = LoggerFormatter.format(format,arguments);
			this.log(SELF,Level.FINEST,ft.getMessage(),ft.getThrowable());
		}
	}

	@Override
	public void debug(String msg){
		if(this.logger.isLoggable(Level.FINE)){
			this.log(SELF,Level.FINE,msg,null);
		}
	}

	@Override
	public void debug(String msg,Throwable t){
		if(this.logger.isLoggable(Level.FINE)){
			this.log(SELF,Level.FINE,msg,t);
		}
	}

	@Override
	public void debug(String format,Object arg){
		if(this.logger.isLoggable(Level.FINE)){
			LoggerFormatterTuple ft = LoggerFormatter.format(format,arg);
			this.log(SELF,Level.FINE,ft.getMessage(),ft.getThrowable());
		}
	}

	@Override
	public void debug(String format,Object argA,Object argB){
		if(this.logger.isLoggable(Level.FINE)){
			LoggerFormatterTuple ft = LoggerFormatter.format(format,argA,argB);
			this.log(SELF,Level.FINE,ft.getMessage(),ft.getThrowable());
		}
	}

	@Override
	public void debug(String format,Object... arguments){
		if(this.logger.isLoggable(Level.FINE)){
			LoggerFormatterTuple ft = LoggerFormatter.format(format,arguments);
			this.log(SELF,Level.FINE,ft.getMessage(),ft.getThrowable());
		}
	}

	@Override
	public void info(String msg){
		if(this.logger.isLoggable(Level.INFO)){
			this.log(SELF,Level.INFO,msg,null);
		}
	}

	@Override
	public void info(String msg,Throwable t){
		if(this.logger.isLoggable(Level.INFO)){
			this.log(SELF,Level.INFO,msg,t);
		}
	}

	@Override
	public void info(String format,Object arg){
		if(this.logger.isLoggable(Level.INFO)){
			LoggerFormatterTuple ft = LoggerFormatter.format(format,arg);
			this.log(SELF,Level.INFO,ft.getMessage(),ft.getThrowable());
		}
	}

	@Override
	public void info(String format,Object argA,Object argB){
		if(this.logger.isLoggable(Level.INFO)){
			LoggerFormatterTuple ft = LoggerFormatter.format(format,argA,argB);
			this.log(SELF,Level.INFO,ft.getMessage(),ft.getThrowable());
		}
	}

	@Override
	public void info(String format,Object... arguments){
		if(this.logger.isLoggable(Level.INFO)){
			LoggerFormatterTuple ft = LoggerFormatter.format(format,arguments);
			this.log(SELF,Level.INFO,ft.getMessage(),ft.getThrowable());
		}
	}

	@Override
	public void warn(String msg){
		if(this.logger.isLoggable(Level.WARNING)){
			this.log(SELF,Level.WARNING,msg,null);
		}
	}

	@Override
	public void warn(String msg,Throwable t){
		if(this.logger.isLoggable(Level.WARNING)){
			this.log(SELF,Level.WARNING,msg,t);
		}
	}

	@Override
	public void warn(String format,Object arg){
		if(this.logger.isLoggable(Level.WARNING)){
			LoggerFormatterTuple ft = LoggerFormatter.format(format,arg);
			this.log(SELF,Level.WARNING,ft.getMessage(),ft.getThrowable());
		}
	}

	@Override
	public void warn(String format,Object argA,Object argB){
		if(this.logger.isLoggable(Level.WARNING)){
			LoggerFormatterTuple ft = LoggerFormatter.format(format,argA,argB);
			this.log(SELF,Level.WARNING,ft.getMessage(),ft.getThrowable());
		}
	}

	@Override
	public void warn(String format,Object... arguments){
		if(this.logger.isLoggable(Level.WARNING)){
			LoggerFormatterTuple ft = LoggerFormatter.format(format,arguments);
			this.log(SELF,Level.WARNING,ft.getMessage(),ft.getThrowable());
		}
	}

	@Override
	public void error(String msg){
		if(this.logger.isLoggable(Level.SEVERE)){
			this.log(SELF,Level.SEVERE,msg,null);
		}
	}

	@Override
	public void error(String msg,Throwable t){
		if(this.logger.isLoggable(Level.SEVERE)){
			this.log(SELF,Level.SEVERE,msg,t);
		}
	}

	@Override
	public void error(String format,Object arg){
		if(this.logger.isLoggable(Level.SEVERE)){
			LoggerFormatterTuple ft = LoggerFormatter.format(format,arg);
			this.log(SELF,Level.SEVERE,ft.getMessage(),ft.getThrowable());
		}
	}

	@Override
	public void error(String format,Object argA,Object argB){
		if(this.logger.isLoggable(Level.SEVERE)){
			LoggerFormatterTuple ft = LoggerFormatter.format(format,argA,argB);
			this.log(SELF,Level.SEVERE,ft.getMessage(),ft.getThrowable());
		}
	}

	@Override
	public void error(String format,Object... arguments){
		if(this.logger.isLoggable(Level.SEVERE)){
			LoggerFormatterTuple ft = LoggerFormatter.format(format,arguments);
			this.log(SELF,Level.SEVERE,ft.getMessage(),ft.getThrowable());
		}
	}

	private void log(String callerFQCN,Level level,String msg,Throwable t){
		LogRecord record = new LogRecord(level,msg);
		record.setLoggerName(this.getName());
		record.setThrown(t);
		fillCallerData(callerFQCN,record);
		this.logger.log(record);
	}

	private static void fillCallerData(String callerFQCN,LogRecord record){
		StackTraceElement[] steArray = new Throwable().getStackTrace();
		int selfIndex = -1;
		for(int i = 0;i < steArray.length;i++){
			final String className = steArray[i].getClassName();
			if(className.equals(callerFQCN) || className.equals(SUPER)){
				selfIndex = i;
				break;
			}
		}
		int found = -1;
		for(int i = selfIndex + 1;i < steArray.length;i++){
			final String className = steArray[i].getClassName();
			if(!(className.equals(callerFQCN) || className.equals(SUPER))){
				found = i;
				break;
			}
		}
		if(found != -1){
			StackTraceElement ste = steArray[found];
			record.setSourceClassName(ste.getClassName());
			record.setSourceMethodName(ste.getMethodName());
		}
	}

}