package com.jcute.core.aop;

import java.lang.reflect.Method;

import com.jcute.core.annotation.Component;
import com.jcute.core.annotation.Interceptor;
import com.jcute.core.aspect.AspectHandler;

@Component
@Interceptor(packages="com.jcute.core.aop.AopEntity")
public class LogAspect extends AspectHandler{
	
	@Override
	public void onBefore(Class<?> targetClass,Method targetMethod,Object[] arguments) throws Throwable{
		System.out.println("before");
	}

	@Override
	public void onAfter(Class<?> targetClass,Method targetMethod,Object[] arguments,Object result) throws Throwable{
		System.out.println("after");
	}

	@Override
	public void onException(Class<?> targetClass,Method targetMethod,Object[] arguments,Throwable exception){
		System.out.println("exception");
	}

	@Override
	public void onBegin(){
		System.out.println("begin");
	}

	@Override
	public void onEnd(){
		System.out.println("end");
	}
	
}