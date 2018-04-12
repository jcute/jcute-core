package com.jcute.core.bean.abs;

import java.util.LinkedHashSet;
import java.util.Set;

import com.jcute.core.bean.BeanDefinition;
import com.jcute.core.bean.BeanFactory;
import com.jcute.core.bean.BeanFactoryProcessor;
import com.jcute.core.config.ConfigManager;
import com.jcute.core.context.ApplicationContext;
import com.jcute.core.error.BeanDefinitionReferenceCycleException;
import com.jcute.core.error.BeanDefinitionSortException;
import com.jcute.core.error.ContextCreateException;
import com.jcute.core.error.ContextDestoryException;
import com.jcute.core.error.ContextInitialException;
import com.jcute.core.error.ContextInjectException;
import com.jcute.core.plugin.PluginManager;

public abstract class AbstractBeanFactory extends AbstractBeanFactoryManager implements BeanFactory{

	private final ApplicationContext applicationContext;
	private final BeanFactoryProcessor beanFactoryProcessor;
	private final PluginManager pluginManager;
	private final ConfigManager configManager;
	private final Set<BeanDefinition> beanDefinitionsSorted;

	public AbstractBeanFactory(ApplicationContext applicationContext){
		if(null == applicationContext){
			throw new IllegalArgumentException("Application context must not be null");
		}
		this.applicationContext = applicationContext;
		this.beanFactoryProcessor = this.createBeanFactoryProcessor();
		this.pluginManager = this.createPluginManager();
		this.configManager = this.createConfigManager();
		this.beanDefinitionsSorted = new LinkedHashSet<BeanDefinition>();
	}

	@Override
	public ApplicationContext getApplicationContext(){
		return this.applicationContext;
	}

	@Override
	public BeanFactoryProcessor getBeanFactoryProcessor(){
		return this.beanFactoryProcessor;
	}

	@Override
	public void initial() throws ContextInitialException{
		try{
			this.beanDefinitionsSorted.clear();
			this.beanDefinitionsSorted.addAll(this.beanFactoryProcessor.getBeanDefinitionsSorted());
			for(BeanDefinition beanDefinition : this.beanDefinitionsSorted){
				beanDefinition.initial();
			}
		}catch(BeanDefinitionReferenceCycleException e){
			throw new ContextInitialException(e.getMessage());
		}catch(BeanDefinitionSortException e){
			throw new ContextInitialException(e.getMessage());
		}
	}

	@Override
	public void destory() throws ContextDestoryException{
		for(BeanDefinition beanDefinition : this.beanDefinitionsSorted){
			beanDefinition.destory();
		}
		this.beanDefinitionsSorted.clear();
		this.clearBeanDefinitions();
	}

	@Override
	public void create() throws ContextCreateException{
		for(BeanDefinition beanDefinition : this.beanDefinitionsSorted){
			beanDefinition.create();
		}
	}

	@Override
	public void inject() throws ContextInjectException{
		for(BeanDefinition beanDefinition : this.beanDefinitionsSorted){
			beanDefinition.inject();
		}
	}

	@Override
	protected BeanFactory getBeanFactory(){
		return this;
	}

	@Override
	public PluginManager getPluginManager(){
		return this.pluginManager;
	}
	
	@Override
	public ConfigManager getConfigManager(){
		return this.configManager;
	}

	protected abstract BeanFactoryProcessor createBeanFactoryProcessor();

	protected abstract PluginManager createPluginManager();

	protected abstract ConfigManager createConfigManager();
	
}