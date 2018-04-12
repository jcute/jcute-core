package com.jcute.core.logging;

/**
 * 日志记录对象,实质上是代理对象,根据系统配置自动选择响应实现
 */
public interface Logger{

	public String getName();

	public boolean isEnabled(LoggerLevel loggerLevel);

	public boolean isTraceEnabled();

	public boolean isDebugEnabled();

	public boolean isInfoEnabled();

	public boolean isWarnEnabled();

	public boolean isErrorEnabled();

	public void trace(String msg);

	public void trace(Throwable t);

	public void trace(String msg,Throwable t);

	public void trace(String format,Object arg);

	public void trace(String format,Object argA,Object argB);

	public void trace(String format,Object... arguments);

	public void debug(String msg);

	public void debug(Throwable t);

	public void debug(String msg,Throwable t);

	public void debug(String format,Object arg);

	public void debug(String format,Object argA,Object argB);

	public void debug(String format,Object... arguments);

	public void info(String msg);

	public void info(Throwable t);

	public void info(String msg,Throwable t);

	public void info(String format,Object arg);

	public void info(String format,Object argA,Object argB);

	public void info(String format,Object... arguments);

	public void warn(String msg);

	public void warn(Throwable t);

	public void warn(String msg,Throwable t);

	public void warn(String format,Object arg);

	public void warn(String format,Object argA,Object argB);

	public void warn(String format,Object... arguments);

	public void error(String msg);

	public void error(Throwable t);

	public void error(String msg,Throwable t);

	public void error(String format,Object arg);

	public void error(String format,Object argA,Object argB);

	public void error(String format,Object... arguments);

	public void log(LoggerLevel level,String msg);

	public void log(LoggerLevel level,Throwable t);

	public void log(LoggerLevel level,String msg,Throwable t);

	public void log(LoggerLevel level,String format,Object arg);

	public void log(LoggerLevel level,String format,Object argA,Object argB);

	public void log(LoggerLevel level,String format,Object... arguments);

}