package com.jcute.core.context.abs;

import java.util.HashMap;
import java.util.Map;

import com.jcute.core.bean.BeanFactory;
import com.jcute.core.context.ApplicationContext;
import com.jcute.core.error.ContextDestoryException;
import com.jcute.core.error.ContextInitialException;
import com.jcute.core.logging.Logger;
import com.jcute.core.logging.LoggerFactory;
import com.jcute.core.plugin.PluginManager;
import com.jcute.core.util.GenericTypeUtil;

public abstract class AbstractApplicationContext implements ApplicationContext{

	private static final Logger logger = LoggerFactory.getLogger(AbstractApplicationContext.class);

	private BeanFactory beanFactory;
	private PluginManager pluginManager;
	private volatile boolean running = false;

	public AbstractApplicationContext(){
		this.beanFactory = this.createBeanFactory();
		this.pluginManager = this.beanFactory.getPluginManager();
	}

	@Override
	public <T> T getBean(Class<T> beanType){
		try{
			return GenericTypeUtil.parseType(this.beanFactory.getBean(beanType));
		}catch(Exception e){
			logger.debug(e.getMessage());
			return null;
		}
	}

	@Override
	public <T> T getBean(String beanName){
		try{
			return GenericTypeUtil.parseType(this.beanFactory.getBean(beanName));
		}catch(Exception e){
			logger.debug(e.getMessage());
			return null;
		}
	}

	@Override
	public <T> T getBean(Class<T> beanType,String beanName){
		try{
			return GenericTypeUtil.parseType(this.beanFactory.getBean(beanType,beanName));
		}catch(Exception e){
			logger.debug(e.getMessage());
			return null;
		}
	}

	@Override
	public <T> Map<String,T> getBeans(Class<T> beanType){
		if(null == beanType){
			return new HashMap<String,T>();
		}
		try{
			return GenericTypeUtil.parseType(this.beanFactory.getBeans(beanType));
		}catch(Exception e){
			logger.debug("Bean definition is blank {}",beanType.getName());
			return new HashMap<String,T>();
		}
	}

	@Override
	public BeanFactory getBeanFactory(){
		return this.beanFactory;
	}

	@Override
	public final synchronized void initial() throws ContextInitialException{
		if(this.running){
			return;
		}
		this.running = true;
		long startTime = System.currentTimeMillis();
		this.doInitial();
		logger.info("------------------------------------------------------");
		logger.info("Application Context Initial Success [{} milliseconds]",(System.currentTimeMillis() - startTime));
		logger.info("------------------------------------------------------");
	}

	@Override
	public final synchronized void destory() throws ContextDestoryException{
		if(!this.running){
			return;
		}
		this.running = false;
		long startTime = System.currentTimeMillis();
		this.doDestory();
		logger.info("------------------------------------------------------");
		logger.info("Application Context Destory Success [{} milliseconds]",(System.currentTimeMillis() - startTime));
		logger.info("------------------------------------------------------");
	}

	protected void doInitial() throws ContextInitialException{
	};

	protected void doDestory() throws ContextDestoryException{
		this.beanFactory.destory();
		this.beanFactory = null;
	};

	protected PluginManager getPluginManager(){
		return this.pluginManager;
	}

	/**
	 * 创建BeanFactory
	 * 
	 * @return 返回Bean工厂
	 */
	protected abstract BeanFactory createBeanFactory();

}