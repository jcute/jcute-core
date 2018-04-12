package com.jcute.plugin.cache.aop;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import com.jcute.core.annotation.Autowired;
import com.jcute.core.annotation.Component;
import com.jcute.core.annotation.Initial;
import com.jcute.core.annotation.Interceptor;
import com.jcute.core.context.ApplicationContext;
import com.jcute.core.error.ContextInitialException;
import com.jcute.core.proxy.Proxy;
import com.jcute.core.proxy.ProxyChain;
import com.jcute.core.util.AnnotationUtil;
import com.jcute.core.util.StringUtil;
import com.jcute.plugin.cache.Cache;
import com.jcute.plugin.cache.CacheClear;
import com.jcute.plugin.cache.CacheEvict;
import com.jcute.plugin.cache.CacheKey;
import com.jcute.plugin.cache.CacheManager;
import com.jcute.plugin.cache.CachePut;
import com.jcute.plugin.cache.Cacheable;
import com.jcute.plugin.cache.imp.DefaultCacheManager;

@Component
@Interceptor(annotations = Cacheable.class)
public class CacheManagerInterceptor implements Proxy{

	private Map<String,CacheManager> cacheManagers = new HashMap<String,CacheManager>();
	private Map<Class<?>,CacheManager> mappingCacheManagers = new HashMap<Class<?>,CacheManager>();

	@Autowired
	private ApplicationContext applicationContext;

	@Initial
	public void initial() throws ContextInitialException{

		this.cacheManagers.clear();
		this.cacheManagers.putAll(this.applicationContext.getBeans(CacheManager.class));

		if(this.cacheManagers.isEmpty()){
			throw new ContextInitialException("CacheManager not found");
		}

	}

	@Override
	public Object execute(ProxyChain proxyChain) throws Throwable{
		Class<?> targetClass = proxyChain.getTargetClass();
		Method targetMethod = proxyChain.getTargetMethod();
		Object[] targetParams = proxyChain.getTargetParams();
		if(this.intercept(targetMethod)){
			CacheManager cacheManager = this.searchCacheManager(targetClass,targetMethod);
			if(null == cacheManager){
				return proxyChain.doProxyChain();
			}
			String cacheKey = this.resolveCacheKey(targetClass,targetMethod,targetParams);
			System.out.println(cacheKey);
			if(AnnotationUtil.hasAnnotation(targetMethod,CachePut.class)){
				if(StringUtil.isBlank(cacheKey)){
					return proxyChain.doProxyChain();
				}
				Cache cache = this.searchCache(cacheManager,targetClass,targetMethod);
				CachePut cachePut = AnnotationUtil.getAnnotation(targetMethod,CachePut.class);
				if(cache.containsKey(cacheKey)){
					System.err.println("命中:" + cacheKey);
					return cache.getValue(cacheKey,Object.class);
				}
				System.err.println("未命中:" + cacheKey);
				Object result = proxyChain.doProxyChain();
				cache.putValue(cacheKey,result,cachePut.cacheExpiry());
				return result;
			}else if(AnnotationUtil.hasAnnotation(targetMethod,CacheEvict.class)){
				if(StringUtil.isBlank(cacheKey)){
					return proxyChain.doProxyChain();
				}
				Cache cache = this.searchCache(cacheManager,targetClass,targetMethod);
				if(!cache.containsKey(cacheKey)){
					return proxyChain.doProxyChain();
				}
				cache.evict(cacheKey);
				return proxyChain.doProxyChain();
			}else if(AnnotationUtil.hasAnnotation(targetMethod,CacheClear.class)){
				Cache cache = this.searchCache(cacheManager,targetClass,targetMethod);
				cache.clear();
				return proxyChain.doProxyChain();
			}else{
				return proxyChain.doProxyChain();
			}
		}else{
			return proxyChain.doProxyChain();
		}
	}

	// ${}
	private String resolveCacheKey(Class<?> targetClass,Method targetMethod,Object[] targetParams){
		String cacheKey = null;
		if(AnnotationUtil.hasAnnotation(targetMethod,CachePut.class)){
			CachePut cachePut = AnnotationUtil.getAnnotation(targetMethod,CachePut.class);
			if(StringUtil.isNotBlank(cachePut.cacheKey())){
				cacheKey = cachePut.cacheKey();
			}
		}
		if(AnnotationUtil.hasAnnotation(targetMethod,CacheEvict.class)){
			CacheEvict cacheEvict = AnnotationUtil.getAnnotation(targetMethod,CacheEvict.class);
			if(StringUtil.isNotBlank(cacheEvict.cacheKey())){
				cacheKey = cacheEvict.cacheKey();
			}
		}
		if(StringUtil.isBlank(cacheKey)){
			return null;
		}
		Map<String,Object> context = new HashMap<String,Object>();
		context.put("targetArguments",targetParams);
		context.put("targetClass",targetClass);
		context.put("targetMethod",targetClass);
		if(null != targetParams && targetParams.length > 0){
			Annotation[] annotations = AnnotationUtil.getAnnotation(targetMethod.getParameterTypes(),targetMethod.getParameterAnnotations(),CacheKey.class);
			for(int i = 0;i < annotations.length;i++){
				if(null == annotations[i]){
					continue;
				}
				CacheKey ck = (CacheKey)annotations[i];
				if(StringUtil.isBlank(ck.value())){
					continue;
				}
				context.put(ck.value(),targetParams[i]);
			}
		}
		return null;
		// Object result = AviatorEvaluator.execute(cacheKey,context);
		// if(null == result){
		// return cacheKey;
		// }
		// return result.toString();
	}

	// 查询指定的缓存对象
	private Cache searchCache(CacheManager cacheManager,Class<?> targetClass,Method targetMethod){
		Cacheable cacheable = AnnotationUtil.getAnnotation(targetClass,Cacheable.class);
		String cacheName = cacheable.cacheName();
		if(AnnotationUtil.hasAnnotation(targetMethod,CachePut.class)){
			CachePut cachePut = AnnotationUtil.getAnnotation(targetMethod,CachePut.class);
			if(StringUtil.isNotBlank(cachePut.cacheName())){
				cacheName = cachePut.cacheName();
			}
		}
		if(AnnotationUtil.hasAnnotation(targetMethod,CacheEvict.class)){
			CacheEvict cacheEvict = AnnotationUtil.getAnnotation(targetMethod,CacheEvict.class);
			if(StringUtil.isNotBlank(cacheEvict.cacheName())){
				cacheName = cacheEvict.cacheName();
			}
		}
		if(AnnotationUtil.hasAnnotation(targetMethod,CacheClear.class)){
			CacheClear cacheClear = AnnotationUtil.getAnnotation(targetMethod,CacheClear.class);
			if(StringUtil.isNotBlank(cacheClear.cacheName())){
				cacheName = cacheClear.cacheName();
			}
		}
		return cacheManager.getCache(cacheName);
	}

	// 查询指定的缓存管理器
	private CacheManager searchCacheManager(Class<?> targetClass,Method targetMethod){
		CacheManager cacheManager = this.mappingCacheManagers.get(targetClass);
		if(null != cacheManager){
			return cacheManager;
		}
		if(null == cacheManager){
			String cacheManagerName = DefaultCacheManager.DEFAULT_CACHEMANAGER_NAME;
			Cacheable cacheable = AnnotationUtil.getAnnotation(targetClass,Cacheable.class);
			if(StringUtil.isNotBlank(cacheable.cacheManager())){
				cacheManagerName = cacheable.cacheManager();
			}
			if(AnnotationUtil.hasAnnotation(targetMethod,CachePut.class)){
				CachePut cachePut = AnnotationUtil.getAnnotation(targetMethod,CachePut.class);
				if(StringUtil.isNotBlank(cachePut.cacheManager())){
					cacheManagerName = cachePut.cacheManager();
				}
			}
			if(AnnotationUtil.hasAnnotation(targetMethod,CacheEvict.class)){
				CacheEvict cacheEvict = AnnotationUtil.getAnnotation(targetMethod,CacheEvict.class);
				if(StringUtil.isNotBlank(cacheEvict.cacheManager())){
					cacheManagerName = cacheEvict.cacheManager();
				}
			}
			if(AnnotationUtil.hasAnnotation(targetMethod,CacheClear.class)){
				CacheClear cacheClear = AnnotationUtil.getAnnotation(targetMethod,CacheClear.class);
				if(StringUtil.isNotBlank(cacheClear.cacheManager())){
					cacheManagerName = cacheClear.cacheManager();
				}
			}
			cacheManager = this.cacheManagers.get(cacheManagerName);
			if(null != cacheManager){
				this.mappingCacheManagers.put(targetClass,cacheManager);
			}
		}
		return cacheManager;
	}

	// 判断当前方法是否启用缓存功能
	private boolean intercept(Method method){
		if(AnnotationUtil.hasAnnotation(method,CachePut.class) || AnnotationUtil.hasAnnotation(method,CacheEvict.class) || AnnotationUtil.hasAnnotation(method,CacheClear.class)){
			return true;
		}
		return false;
	}

}