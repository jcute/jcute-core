package com.jcute.core.proxy;

import java.lang.reflect.Method;

import com.jcute.core.bean.BeanDefinition;
import com.jcute.core.error.BeanInstanceNotFoundException;

import net.sf.cglib.proxy.MethodProxy;

/**
 * 代理链
 * 
 * @author tangbin
 *
 */
public class ProxyChain{
	
	private final Class<?> targetClass;
	private final Object targetObject;
	private final Method targetMethod;
	private final Object[] targetParams;
	private final MethodProxy methodProxy;
	
	private BeanDefinition[] proxys = new BeanDefinition[0];
	private int invokeIndex = 0;
	
	public ProxyChain(Class<?> targetClass,Object targetObject,Method targetMethod,Object[] targetParams,MethodProxy methodProxy,BeanDefinition[] proxys){
		this.targetClass = targetClass;
		this.targetObject = targetObject;
		this.targetMethod = targetMethod;
		this.targetParams = targetParams;
		this.methodProxy = methodProxy;
		this.proxys = proxys;
	}
	
	public Class<?> getTargetClass(){
		return targetClass;
	}

	public Object getTargetObject(){
		return targetObject;
	}

	public Method getTargetMethod(){
		return targetMethod;
	}

	public Object[] getTargetParams(){
		return targetParams;
	}

	public MethodProxy getMethodProxy(){
		return methodProxy;
	}
	
	public Object doProxyChain()throws Throwable{
		Object methodResult = null;
		if(this.invokeIndex < this.proxys.length){
			Proxy proxy = this.getProxyInstance(this.proxys[this.invokeIndex++]);
			if(null == proxy){
				throw new IllegalStateException("Proxy instance is null");
			}
			methodResult = proxy.execute(this);
		}else{
			methodResult = this.methodProxy.invokeSuper(this.targetObject,this.targetParams);
		}
		return methodResult;
	}
	
	private Proxy getProxyInstance(BeanDefinition beanDefinition){
		try{
			Object result = beanDefinition.getBeanInstance();
			if(null != result && result instanceof Proxy){
				return (Proxy)result;
			}
		}catch(BeanInstanceNotFoundException e){
			e.printStackTrace();
		}
		return null;
	}

}