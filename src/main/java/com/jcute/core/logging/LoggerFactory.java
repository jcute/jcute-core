package com.jcute.core.logging;

import com.jcute.core.logging.imp.LoggerFactoryForJDK;
import com.jcute.core.logging.imp.LoggerFactoryForLOG4J;
import com.jcute.core.logging.imp.LoggerFactoryForSLF4J;

/**
 * Logger工厂,根据系统配置及类的信息,自动选择使用的日志<br/>
 * 选举次序: slf4j -> log4j -> jdk
 */
public abstract class LoggerFactory{

	private static volatile LoggerFactory defaultFactory;

	private static LoggerFactory createDefaultFactory(String name){
		LoggerFactory loggerFactory;
		try{
			Class.forName("org.slf4j.impl.StaticLoggerBinder");
			loggerFactory = new LoggerFactoryForSLF4J(true);
			loggerFactory.newInstance(name).debug("using slf4j as the default logging framework");
		}catch(Throwable t){
			try{
				loggerFactory = new LoggerFactoryForLOG4J();
				loggerFactory.newInstance(name).debug("using log4j as the default logging framework");
			}catch(Throwable tt){
				loggerFactory = new LoggerFactoryForJDK();
				loggerFactory.newInstance(name).debug("using java.util.logging as the default logging framework");
			}
		}
		return loggerFactory;
	}

	public static LoggerFactory getDefaultFactory(){
		if(null == defaultFactory){
			defaultFactory = createDefaultFactory(LoggerFactory.class.getName());
		}
		return defaultFactory;
	}

	public static void setDefaultFactory(LoggerFactory defaultFactory){
		if(null == defaultFactory){
			throw new NullPointerException("defaultFactory");
		}
		LoggerFactory.defaultFactory = defaultFactory;
	}

	public static Logger getLogger(Class<?> requireType){
		return getLogger(requireType.getName());
	}

	public static Logger getLogger(String name){
		return getDefaultFactory().newInstance(name);
	}

	protected abstract Logger newInstance(String name);

}