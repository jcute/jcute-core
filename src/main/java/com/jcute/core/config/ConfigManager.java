package com.jcute.core.config;

import java.util.Set;

import com.jcute.core.resource.Resource;

/**
 * 属性管理器
 * @author koko
 *
 */
public interface ConfigManager{

	public void addConfig(Config config);
	public void addConfig(Resource resource,ConfigType configType);

	public Set<Config> getAllConfigs();

	public <T> T getConfigValue(String configName,Class<T> type);

}