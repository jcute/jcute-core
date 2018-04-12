package com.jcute.core.config.imp;

public class ConfigBySystemProperties extends ConfigByProperties{

	public ConfigBySystemProperties(){
		super(System.getProperties());
	}
	
}