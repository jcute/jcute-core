package com.jcute.core.proxy;

import java.lang.reflect.Method;

import org.objenesis.Objenesis;
import org.objenesis.ObjenesisStd;
import org.objenesis.instantiator.ObjectInstantiator;

import com.jcute.core.bean.BeanDefinition;
import com.jcute.core.util.GenericTypeUtil;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.Factory;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

/**
 * 代理创建工具类
 * 
 * @author tangbin
 *
 */
public class ProxyManager{

	/**
	 * 创建代理对象,并根据传入的参数进行实例化
	 * 
	 * @param targetClass
	 * @param proxyList
	 * @param parameterTypes
	 * @param parameterDatas
	 * @return 代理对象
	 */
	public static <T> T createProxy(final Class<?> targetClass,final BeanDefinition[] proxys,Class<?>[] parameterTypes,Object[] parameterDatas){
		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(targetClass);
		enhancer.setCallback(new MethodInterceptor(){
			@Override
			public Object intercept(Object targetObject,Method targetMethod,Object[] methodParams,MethodProxy methodProxy) throws Throwable{
				return new ProxyChain(targetClass,targetObject,targetMethod,methodParams,methodProxy,proxys).doProxyChain();
			}
		});
		if(null == parameterTypes || parameterTypes.length == 0){
			return GenericTypeUtil.parseType(enhancer.create());
		}else{
			return GenericTypeUtil.parseType(enhancer.create(parameterTypes,parameterDatas));
		}
	}
	
	/**
	 * 创建代理对象，使用无参构造函数实例化
	 * 
	 * @param targetClass
	 * @param proxyList
	 * @return 代理对象
	 */
	public static <T> T createProxy(final Class<?> targetClass,final BeanDefinition[] proxys){
		return createProxy(targetClass,proxys,null,null);
	}
	
	/**
	 * 为对象创建代理
	 * @param targetObject
	 * @param proxys
	 * @param parameterTypes
	 * @param parameterDatas
	 * @return 代理对象
	 */
	public static <T> T createProxy(final Object targetObject,final BeanDefinition[] proxys){
		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(targetObject.getClass());
		enhancer.setCallbackType(MethodInterceptor.class);
		Class<?> clazz = enhancer.createClass();
		Objenesis objenesis = new ObjenesisStd();
		ObjectInstantiator<?> thingInstantiator = objenesis.getInstantiatorOf(clazz);
		Object proxyInstance = thingInstantiator.newInstance();
		Factory factory = (Factory)proxyInstance;
		factory.setCallback(0,new MethodInterceptor(){
			@Override
			public Object intercept(Object targetObject,Method targetMethod,Object[] methodParams,MethodProxy methodProxy) throws Throwable{
				return new ProxyChain(targetObject.getClass(),targetObject,targetMethod,methodParams,methodProxy,proxys).doProxyChain();
			}
		});
		
		return GenericTypeUtil.parseType(factory);
	}
	
}