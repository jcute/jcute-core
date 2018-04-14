package com.jcute.plugin.cache;

import java.util.Map;

public interface CacheKeyPolicy{
	
	public Object getCacheKey(Map<String,Object> context,String expression);
	
}