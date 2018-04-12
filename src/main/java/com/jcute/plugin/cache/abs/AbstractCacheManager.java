package com.jcute.plugin.cache.abs;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;

import com.jcute.core.util.StringUtil;
import com.jcute.plugin.cache.Cache;
import com.jcute.plugin.cache.CacheManager;

public abstract class AbstractCacheManager implements CacheManager{
	
	private ConcurrentHashMap<String,Cache> caches = new ConcurrentHashMap<String,Cache>();
	
	@Override
	public Cache getCache(String name){
		Cache cache = this.caches.get(name);
		if(null == cache){
			this.caches.put(name,this.createCache(name));
			cache = this.caches.get(name);
		}
		return cache;
	}
	
	@Override
	public Collection<String> getCacheNames(){
		return this.caches.keySet();
	}

	@Override
	public void setCacheNames(Collection<String> cacheNames){
		if(null == cacheNames || cacheNames.isEmpty()){
			return;
		}
		for(String name : cacheNames){
			if(StringUtil.isBlank(name) || this.caches.containsKey(name)){
				continue;
			}
			this.caches.put(name,this.createCache(name));
		}
	}
	
	protected abstract Cache createCache(String name);
	
}