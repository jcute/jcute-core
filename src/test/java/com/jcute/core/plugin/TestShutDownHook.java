package com.jcute.core.plugin;

import com.jcute.core.annotation.Configuration;
import com.jcute.core.boot.JCuteApplication;
import com.jcute.plugin.shutdown.EnableShutDownHook;

@Configuration
@EnableShutDownHook
public class TestShutDownHook{
	
	public static void main(String[] args){
		JCuteApplication.run(TestShutDownHook.class);
	}
	
}