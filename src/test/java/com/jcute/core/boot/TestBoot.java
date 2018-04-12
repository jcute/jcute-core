package com.jcute.core.boot;

import com.jcute.core.annotation.ComponentScan;
import com.jcute.core.annotation.Configuration;

@Configuration
@ComponentScan("com.jcute.core.bean")
public class TestBoot{
	
	public static void main(String[] args){
		JCuteApplication.run(TestBoot.class);
	}
	
}