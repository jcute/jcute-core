package com.jcute.core.junit;

import org.junit.Test;
import org.junit.runner.RunWith;

import com.jcute.core.annotation.Autowired;
import com.jcute.core.annotation.ComponentScan;
import com.jcute.core.annotation.Configuration;
import com.jcute.core.context.ApplicationContext;

@RunWith(JCuteJUnitForClassRunner.class)
@Configuration
@ComponentScan("com.jcute.core.bean")
public class TestJUnitRunnerClass{
	
	@Autowired
	private ApplicationContext applicationContext;
	
	@Test
	public void testRun() throws Exception{
		System.out.println(this);
		System.out.println(this.applicationContext.getBeanFactory().getAllBeanDefinitions());
	}
	
}