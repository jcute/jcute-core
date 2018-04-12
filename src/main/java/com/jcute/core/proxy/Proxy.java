package com.jcute.core.proxy;

/**
 * 代理处理接口
 */
public interface Proxy{
	
	/**
     * 执行链式代理
     *
     * @param proxyChain 代理链
     * @return 目标方法返回值
     * @throws Throwable 异常
     */
    Object execute(ProxyChain proxyChain) throws Throwable;
	
}