package com.jcute.plugin.cache;

import org.junit.Test;

import com.jcute.plugin.cache.imp.DefaultCacheManager;

public class TestCacheManager{

	@Test
	public void testDefaultCacheManager() throws InterruptedException{

		CacheManager cacheManager = new DefaultCacheManager("demo","demo","test");
		System.out.println(cacheManager.getCacheNames());
		Cache cache = cacheManager.getCache("demo");
		cache.putValue("name","测试");
		System.out.println(cache.getValue("name").getValue());

		cache.putValue("info","测试信息",5000);// 五秒后作废

		for(int i = 0;i < 7;i++){
			System.out.println(i + "秒:" + cache.getValue("info").getValue());

			Thread.sleep(1000);
		}

	}
	
}