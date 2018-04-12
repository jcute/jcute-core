package com.jcute.plugin.cache.imp;

import com.jcute.plugin.cache.CacheValueWrapper;

public class DefaultCacheValueWrapper implements CacheValueWrapper{

	private static final long serialVersionUID = 2694606001858682684L;

	private Object value;

	public DefaultCacheValueWrapper(Object value){
		this.value = value;
	}

	@Override
	public Object getValue(){
		return this.value;
	}

}