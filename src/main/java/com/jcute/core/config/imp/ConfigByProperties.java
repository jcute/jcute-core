package com.jcute.core.config.imp;

import java.util.Properties;

import com.jcute.core.config.abs.AbstractConfig;

public class ConfigByProperties extends AbstractConfig{
	
	private Properties properties = new Properties();

	public ConfigByProperties(Properties properties){
		if(null != properties && properties.size() > 0){
			this.properties.putAll(properties);
		}
	}

	@Override
	public boolean containsName(String name){
		return this.properties.containsKey(name);
	}
	
	@Override
	protected String getValue(String name){
		return this.properties.getProperty(name,null);
	}
	
}