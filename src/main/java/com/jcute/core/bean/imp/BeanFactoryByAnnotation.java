package com.jcute.core.bean.imp;

import com.jcute.core.bean.BeanFactoryProcessor;
import com.jcute.core.bean.abs.AbstractBeanFactory;
import com.jcute.core.config.ConfigManager;
import com.jcute.core.config.imp.ConfigManagerByDefault;
import com.jcute.core.context.ApplicationContext;
import com.jcute.core.plugin.PluginManager;
import com.jcute.core.plugin.imp.PluginManagerByAnnotation;

public class BeanFactoryByAnnotation extends AbstractBeanFactory{

	public BeanFactoryByAnnotation(ApplicationContext applicationContext){
		super(applicationContext);
	}

	@Override
	protected PluginManager createPluginManager(){
		return new PluginManagerByAnnotation();
	}
	
	@Override
	protected ConfigManager createConfigManager(){
		return new ConfigManagerByDefault();
	}
	
	@Override
	protected BeanFactoryProcessor createBeanFactoryProcessor(){
		return new BeanFactoryProcessorByAnnotation(this);
	}
	
}