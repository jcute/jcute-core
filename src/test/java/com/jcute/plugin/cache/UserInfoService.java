package com.jcute.plugin.cache;

import com.jcute.core.annotation.Component;

@Component
@Cacheable(cacheName="userInfo")
public class UserInfoService{
	
	@CachePut(cacheKey="targetMethod.getName()",cacheExpiry=5000)//有效期5秒
	public String getName(){
		return "UserInfoService";
	}
	
	@CacheEvict(cacheKey="asd")
	public void clearEvict(){
		System.out.println("来自方法清除:asd");
	}
	
	@CacheClear
	public void clear(){
		System.out.println("来自方法清空");
	}
	
}