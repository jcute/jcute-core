package com.jcute.plugin.cache.abs;

import com.jcute.core.util.GenericTypeUtil;
import com.jcute.core.util.StringUtil;
import com.jcute.plugin.cache.Cache;
import com.jcute.plugin.cache.CacheValueWrapper;

public abstract class AbstractCache implements Cache{

	private String name;

	public AbstractCache(String name){
		if(StringUtil.isBlank(name)){
			throw new IllegalArgumentException("Cache name must not be null");
		}
		this.name = name;
	}

	@Override
	public String getName(){
		return this.name;
	}

	@Override
	public CacheValueWrapper getValue(Object key){
		return this.searchCacheValueWrapper(key);
	}

	@Override
	public <T>T getValue(Object key,Class<T> type){
		return GenericTypeUtil.parseType(this.searchCacheValueWrapper(key).getValue());
	}
	
	@Override
	public void putValue(Object key,Object value){
		this.putValue(key,value,0);
	}
	
	protected abstract CacheValueWrapper createCacheValueWrapper(Object key,Object value);
	protected abstract CacheValueWrapper searchCacheValueWrapper(Object key);

}