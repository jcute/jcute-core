package com.jcute.core.context.imp;

import java.util.Set;

import com.jcute.core.bean.BeanFactory;
import com.jcute.core.bean.BeanFactoryProcessor;
import com.jcute.core.bean.BeanScope;
import com.jcute.core.bean.imp.BeanDefinitionByInstance;
import com.jcute.core.bean.imp.BeanFactoryByAnnotation;
import com.jcute.core.context.ApplicationContext;
import com.jcute.core.context.abs.AbstractApplicationContext;
import com.jcute.core.error.BeanDefinitionMultipleException;
import com.jcute.core.error.ContextCreateException;
import com.jcute.core.error.ContextDestoryException;
import com.jcute.core.error.ContextInitialException;
import com.jcute.core.error.ContextInjectException;
import com.jcute.core.scan.PackageScanner;
import com.jcute.core.scan.PackageScannerFilter;
import com.jcute.core.scan.imp.DefaultPackageScanner;

/**
 * 基于注解的ApplicationContext,是否基于其他配置方式可自行扩展
 * 
 * @author koko
 *
 */
public class ApplicationContextByAnnotation extends AbstractApplicationContext{

	private final Class<?> runnerClass;

	public ApplicationContextByAnnotation(Class<?> runnerClass){
		super();
		if(null == runnerClass){
			throw new IllegalArgumentException("Runner class must not be null");
		}
		this.runnerClass = runnerClass;
	}

	@Override
	protected void doInitial() throws ContextInitialException{
		String packageName = "";
		if(null != this.runnerClass.getPackage()){
			packageName = this.runnerClass.getPackage().getName();
		}
		BeanFactory beanFactory = this.getBeanFactory();
		BeanFactoryProcessor beanFactoryProcessor = beanFactory.getBeanFactoryProcessor();
		PackageScannerFilter packageScannerFilter = (PackageScannerFilter)beanFactoryProcessor;
		Set<String> patterns = beanFactoryProcessor.searchScanPattern(packageName);
		PackageScanner packageScanner = new DefaultPackageScanner();
		this.getPluginManager().onBeforeScanning(packageScanner);
		packageScanner.addPattern(packageName);
		packageScanner.addPattern(patterns);
		packageScanner.addPackageScannerFilter(packageScannerFilter);
		try{
			beanFactory.addBeanDefinition(new BeanDefinitionByInstance(beanFactory,ApplicationContext.class,null,BeanScope.Singleton,this));
			beanFactory.addBeanDefinition(new BeanDefinitionByInstance(beanFactory,BeanFactory.class,null,BeanScope.Singleton,beanFactory));
			beanFactory.addBeanDefinition(new BeanDefinitionByInstance(beanFactory,BeanFactoryProcessor.class,null,BeanScope.Singleton,beanFactoryProcessor));
			for(Class<?> target : packageScanner.scan()){
				beanFactory.addBeanDefinition(target);
			}
			this.getPluginManager().onBeforeInitial();
			beanFactory.initial();
		}catch(BeanDefinitionMultipleException e){
			e.printStackTrace();
		}

		try{
			this.getPluginManager().onBeforeCreate();
			beanFactory.create();
		}catch(ContextCreateException e){
			throw new ContextInitialException("Bean instance create failed",e);
		}

		try{
			this.getPluginManager().onBeforeInject();
			beanFactory.inject();
			this.getPluginManager().onAfterInject();
		}catch(ContextInjectException e){
			throw new ContextInitialException("Bean instance inject failed",e);
		}

	}

	@Override
	protected void doDestory() throws ContextDestoryException{
		this.getPluginManager().onBeforeDestory();
		super.doDestory();
	}

	@Override
	protected BeanFactory createBeanFactory(){
		return new BeanFactoryByAnnotation(this);
	}

}