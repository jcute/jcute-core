package com.jcute.core.plugin;

import java.lang.annotation.Annotation;

import com.jcute.core.bean.BeanFactory;
import com.jcute.core.context.ApplicationContext;
import com.jcute.core.error.ContextCreateException;
import com.jcute.core.error.ContextDestoryException;
import com.jcute.core.error.ContextInitialException;
import com.jcute.core.error.ContextInjectException;
import com.jcute.core.scan.PackageScanner;
import com.jcute.core.util.GenericTypeUtil;

/**
 * 插件机制实现
 * 
 * @author tangbin
 *
 */
public abstract class Plugin{

	private final ApplicationContext applicationContext;
	private final BeanFactory beanFactory;
	private final Annotation annotation;

	public Plugin(ApplicationContext applicationContext,Annotation annotation){
		if(null == applicationContext){
			throw new IllegalArgumentException("Application context must not be null");
		}
		if(null == annotation){
			throw new IllegalArgumentException("Plugin annotation must not be null");
		}
		this.applicationContext = applicationContext;
		this.beanFactory = applicationContext.getBeanFactory();
		this.annotation = annotation;
	}

	public final ApplicationContext getApplicationContext(){
		return applicationContext;
	}

	public final BeanFactory getBeanFactory(){
		return beanFactory;
	}

	public final <T extends Annotation> T getAnnotation(){
		return GenericTypeUtil.parseType(this.annotation);
	}

	public void onBeforeScanning(PackageScanner packageScanner) throws ContextInitialException{
	}

	public void onBeforeInitial() throws ContextInitialException{
	}

	public void onBeforeCreate() throws ContextCreateException{
	}

	public void onBeforeInject() throws ContextInjectException{
	}

	public void onAfterInject() throws ContextInjectException{
	}

	public void onBeforeDestory() throws ContextDestoryException{
	}

}