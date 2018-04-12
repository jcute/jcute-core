package com.jcute.core.logging.imp;

import com.jcute.core.logging.Logger;
import com.jcute.core.logging.LoggerFactory;

/**
 * 工厂对象创建log4j代理对象
 */
public class LoggerFactoryForLOG4J extends LoggerFactory{

	@Override
	protected Logger newInstance(String name){
		return new LoggerForLOG4J(org.apache.log4j.Logger.getLogger(name));
	}

}