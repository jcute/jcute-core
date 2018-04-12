package com.jcute.core.context;

import com.jcute.core.annotation.Component;
import com.jcute.core.annotation.Interceptor;
import com.jcute.core.logging.Logger;
import com.jcute.core.logging.LoggerFactory;
import com.jcute.core.proxy.Proxy;
import com.jcute.core.proxy.ProxyChain;

@Component
@Interceptor(annotations = Component.class)
public class LoggingInterceptor implements Proxy{

	private static final Logger logger = LoggerFactory.getLogger(LoggingInterceptor.class);

	@Override
	public Object execute(ProxyChain proxyChain) throws Throwable{
		logger.debug("before");
		Object result = proxyChain.doProxyChain();
		logger.debug("after");
		return result;
	}

}