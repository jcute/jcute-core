package com.jcute.core.config;

import com.jcute.core.resource.Resource;

public interface ConfigConverter<R>{
	
	public R convert(Resource resource);
	
}