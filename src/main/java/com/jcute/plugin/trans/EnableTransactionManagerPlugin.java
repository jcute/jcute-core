package com.jcute.plugin.trans;

import java.lang.annotation.Annotation;

import com.jcute.core.context.ApplicationContext;
import com.jcute.core.plugin.Plugin;

public class EnableTransactionManagerPlugin extends Plugin{

	public EnableTransactionManagerPlugin(ApplicationContext applicationContext,Annotation annotation){
		super(applicationContext,annotation);
	}
	
	
	
}