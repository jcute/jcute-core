package com.jcute.plugin.cache;

import java.util.Collection;

/**
 * 缓存管理器
 * 
 * @author tangbin
 * 
 */
public interface CacheManager{

	/**
	 * @param name
	 * @return 返回缓存对象
	 */
	public Cache getCache(String name);

	/**
	 * @return 返回所有缓存对象名称
	 */
	public Collection<String> getCacheNames();

	/**
	 * 初始化缓存
	 * 
	 * @param cacheNames
	 */
	public void setCacheNames(Collection<String> cacheNames);

}