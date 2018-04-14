package com.jcute.plugin.cache.abs;

import java.util.HashMap;
import java.util.Map;

import com.jcute.core.util.StringUtil;
import com.jcute.plugin.cache.CacheKeyPolicy;

public abstract class AbstractCacheKeyPolicy implements CacheKeyPolicy{

	@Override
	public Object getCacheKey(Map<String,Object> context,String expression){
		if(null == context){
			context = new HashMap<String,Object>();
		}
		if(StringUtil.isBlank(expression)){
			expression = "this";
		}
		return this.doGetCacheKey(context,expression);
	}
	
	protected abstract Object doGetCacheKey(Map<String,Object> context,String expression);
	
}