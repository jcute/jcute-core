package com.jcute.core.plugin;

import com.jcute.core.annotation.Configuration;
import com.jcute.core.boot.JCuteApplication;

@Configuration
@EnableDemo
public class TestPlugin{
	
	public static void main(String[] args){
		JCuteApplication.run(TestPlugin.class);
	}
	
}