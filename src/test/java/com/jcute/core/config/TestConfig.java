package com.jcute.core.config;

import org.junit.Test;

import com.jcute.core.config.imp.ConfigManagerByDefault;
import com.jcute.core.resource.Resource;
import com.jcute.core.util.ResourceUtil;

public class TestConfig{
	
	@Test
	public void testReadConfig(){
		
		Resource resource = ResourceUtil.getResource("/com/jcute/core/config/test.properties");
		ConfigManager configManager = new ConfigManagerByDefault();
		configManager.addConfig(resource,ConfigType.Properties);
		
		for(Config config : configManager.getAllConfigs()) {
			System.out.println(config);
		}
		
//		String value = configManager.getConfigValue("os.name",String.class);
//		System.out.println(value);
		
		int a = configManager.getConfigValue("server.port:99",int.class);
		System.out.println(a);
		
	}
	
}