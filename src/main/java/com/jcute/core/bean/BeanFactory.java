package com.jcute.core.bean;

import com.jcute.core.config.ConfigManager;
import com.jcute.core.context.ApplicationContext;
import com.jcute.core.plugin.PluginManager;
import com.jcute.core.toolkit.Lifecycle;

/**
 * Bean管理工厂
 * 
 * @author koko
 *
 */
public interface BeanFactory extends Lifecycle, BeanFactoryManager{

	/**
	 * 返回Bean处理器
	 * 
	 * @return
	 */
	public BeanFactoryProcessor getBeanFactoryProcessor();

	public PluginManager getPluginManager();

	public ConfigManager getConfigManager();

	public ApplicationContext getApplicationContext();

}