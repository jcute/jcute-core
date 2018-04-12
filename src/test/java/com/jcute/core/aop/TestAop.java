package com.jcute.core.aop;

import com.jcute.core.annotation.Configuration;
import com.jcute.core.context.ApplicationContext;
import com.jcute.core.context.imp.ApplicationContextByAnnotation;

@Configuration
public class TestAop{
	
	public static void main(String[] args) throws Exception{
		ApplicationContext applicationContext = new ApplicationContextByAnnotation(TestAop.class);
		applicationContext.initial();
		
		AopEntity aopEntity = applicationContext.getBean(AopEntity.class);
		System.out.println(aopEntity.getName());
		
		applicationContext.destory();
	}
	
}