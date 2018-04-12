package com.jcute.core.config;

import com.jcute.core.annotation.Configuration;
import com.jcute.core.annotation.ImportConfig;
import com.jcute.core.annotation.Initial;
import com.jcute.core.annotation.Property;
import com.jcute.core.boot.JCuteApplication;

@Configuration(configs = {
	@ImportConfig(value = "server.properties"),
	@ImportConfig("demo.properties")
})
public class TestConfigAutowired{
	
	@Property("server.port:8989")
	private int port;
	
	@Property("server.name:hello")
	private String name;
	
	public static void main(String[] args){
		JCuteApplication.run(TestConfigAutowired.class);
	}
	
	@Initial
	public void init() {
		System.out.println(this.port);
		System.out.println(this.name);
	}
	
}