package com.jcute.plugin.cache;

import org.junit.Test;
import org.junit.runner.RunWith;

import com.jcute.core.annotation.Autowired;
import com.jcute.core.annotation.Configuration;
import com.jcute.core.junit.JCuteJUnitForClassRunner;

@RunWith(JCuteJUnitForClassRunner.class)
@Configuration
@EnableCacheManager
public class TestCacheAnnotation{

	@Autowired
	private UserInfoService userInfoService;

	@Test
	public void testRun() throws InterruptedException{
		for(int i = 0;i < 15;i++){
			System.out.println(this.userInfoService.getName());
			Thread.sleep(1000);
			if(i == 8){
				this.userInfoService.clearEvict();
			}
			if(i == 10){
				this.userInfoService.clear();
			}
		}

	}

}