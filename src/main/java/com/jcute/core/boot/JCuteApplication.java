package com.jcute.core.boot;

import com.jcute.core.context.ApplicationContext;
import com.jcute.core.context.imp.ApplicationContextByAnnotation;
import com.jcute.core.error.ContextInitialException;

public class JCuteApplication{

	private ApplicationContext applicationContext;

	private JCuteApplication(Class<?> runnerClass){
		try{
			this.applicationContext = new ApplicationContextByAnnotation(runnerClass);
			this.applicationContext.initial();
		}catch(ContextInitialException e){
			System.exit(1);
		}
	}

	public ApplicationContext getApplicationContext(){
		return this.applicationContext;
	}
	
	public static JCuteApplication run(Class<?> runnerClass){
		return new JCuteApplication(runnerClass);
	}

}