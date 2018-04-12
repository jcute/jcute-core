package com.jcute.plugin.cache.imp;

import java.util.Arrays;

import com.jcute.plugin.cache.Cache;
import com.jcute.plugin.cache.abs.AbstractCacheManager;

public class DefaultCacheManager extends AbstractCacheManager{
	
	public static final String DEFAULT_CACHEMANAGER_NAME = DefaultCacheManager.class.getName();
	
	public DefaultCacheManager(String...names){
		if(null != names && names.length > 0){
			this.setCacheNames(Arrays.asList(names));
		}
	}
	
	@Override
	protected Cache createCache(String name){
		return new DefaultCache(name);
	}
	
}