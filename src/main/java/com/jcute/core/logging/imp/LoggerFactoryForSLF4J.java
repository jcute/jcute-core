package com.jcute.core.logging.imp;

import org.slf4j.helpers.NOPLoggerFactory;

import com.jcute.core.logging.Logger;
import com.jcute.core.logging.LoggerFactory;

/**
 * 工厂对象创建slf4j代理对象
 */
public class LoggerFactoryForSLF4J extends LoggerFactory{

	public LoggerFactoryForSLF4J(boolean failIfNOP){
		assert failIfNOP;
		if(org.slf4j.LoggerFactory.getILoggerFactory() instanceof NOPLoggerFactory){
			throw new NoClassDefFoundError("NOPLoggerFactory not supported");
		}
	}

	@Override
	protected Logger newInstance(String name){
		return new LoggerForSLF4J(org.slf4j.LoggerFactory.getLogger(name));
	}

}