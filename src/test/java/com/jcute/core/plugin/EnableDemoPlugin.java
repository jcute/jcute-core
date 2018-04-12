package com.jcute.core.plugin;

import java.lang.annotation.Annotation;

import com.jcute.core.context.ApplicationContext;
import com.jcute.core.error.ContextCreateException;
import com.jcute.core.error.ContextDestoryException;
import com.jcute.core.error.ContextInitialException;
import com.jcute.core.error.ContextInjectException;
import com.jcute.core.scan.PackageScanner;

public class EnableDemoPlugin extends Plugin{

	public EnableDemoPlugin(ApplicationContext applicationContext,Annotation annotation){
		super(applicationContext,annotation);
	}

	@Override
	public void onBeforeScanning(PackageScanner packageScanner) throws ContextInitialException{
		System.out.println("onBeforeScanning");
	}

	@Override
	public void onBeforeInitial() throws ContextInitialException{
		System.out.println("onBeforeInitial");
	}

	@Override
	public void onBeforeCreate() throws ContextCreateException{
		System.out.println("onBeforeCreate");
	}

	@Override
	public void onBeforeInject() throws ContextInjectException{
		System.out.println("onBeforeInject");
	}

	@Override
	public void onAfterInject() throws ContextInjectException{
		System.out.println("onAfterInject");
	}

	@Override
	public void onBeforeDestory() throws ContextDestoryException{
		System.out.println("onBeforeDestory");
	}
	
}