package com.jcute.core.logging.imp;

import com.jcute.core.logging.Logger;
import com.jcute.core.logging.LoggerFactory;

/**
 * 工厂对象创建JDK自带logger
 */
public class LoggerFactoryForJDK extends LoggerFactory{

	@Override
	protected Logger newInstance(String name){
		return new LoggerForJDK(java.util.logging.Logger.getLogger(name));
	}

}