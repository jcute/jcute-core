package com.jcute.core.aspect;

import java.lang.reflect.Method;

import com.jcute.core.proxy.Proxy;
import com.jcute.core.proxy.ProxyChain;

public abstract class AspectHandler implements Proxy{

	@Override
	public final Object execute(ProxyChain proxyChain) throws Throwable{
		Object result = null;
		Class<?> targetClass = proxyChain.getTargetClass();
		Method targetMethod = proxyChain.getTargetMethod();
		Object[] arguments = proxyChain.getTargetParams();
		this.onBegin();
		try{
			if(this.onIntercept(targetClass,targetMethod,arguments)){
				this.onBefore(targetClass,targetMethod,arguments);
				result = proxyChain.doProxyChain();
				this.onAfter(targetClass,targetMethod,arguments,result);
			}else{
				result = proxyChain.doProxyChain();
			}
		}catch(Throwable t){
			this.onException(targetClass,targetMethod,arguments,t);
			throw t;
		}finally{
			this.onEnd();
		}
		return result;
	}

	/**
	 * 判断当前方法是否需要拦截，默认返回true，自定义可重写此方法
	 * 
	 * @param targetClass
	 * @param targetMethod
	 * @param arguments
	 * @return true为拦截
	 * @throws Throwable
	 */
	public boolean onIntercept(Class<?> targetClass,Method targetMethod,Object[] arguments) throws Throwable{
		return true;
	}

	/**
	 * 方法调用前执行
	 * 
	 * @param targetClass
	 * @param targetMethod
	 * @param arguments
	 * @throws Throwable
	 */
	public void onBefore(Class<?> targetClass,Method targetMethod,Object[] arguments) throws Throwable{
	}

	/**
	 * 方法调用后执行
	 * 
	 * @param targetClass
	 * @param targetMethod
	 * @param arguments
	 * @param result
	 * @throws Throwable
	 */
	public void onAfter(Class<?> targetClass,Method targetMethod,Object[] arguments,Object result) throws Throwable{
	}

	/**
	 * 方法调用出现异常执行
	 * 
	 * @param targetClass
	 * @param targetMethod
	 * @param arguments
	 * @param exception
	 */
	public void onException(Class<?> targetClass,Method targetMethod,Object[] arguments,Throwable exception){
	}

	/**
	 * 最前面执行
	 */
	public void onBegin(){

	}

	/**
	 * 最后面执行
	 */
	public void onEnd(){
	}

}