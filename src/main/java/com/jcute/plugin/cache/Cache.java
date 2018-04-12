package com.jcute.plugin.cache;


/**
 * 缓存对象
 * 
 * @author tangbin
 * 
 */
public interface Cache{

	/**
	 * @return 返回缓存名称
	 */
	public String getName();

	/**
	 * @return 返回缓存原始对象
	 */
	public Object getNativeCache();

	/**
	 * 判断指定的key是否存在
	 * 
	 * @param key
	 * @return
	 */
	public boolean containsKey(Object key);

	/**
	 * @param key
	 * @return 返回value持有器
	 */
	public CacheValueWrapper getValue(Object key);

	/**
	 * @param key
	 * @param type
	 * @return 返回缓存value
	 */
	public <T>T getValue(Object key,Class<T> type);

	/**
	 * 缓存数据
	 * 
	 * @param key
	 * @param value
	 */
	public void putValue(Object key,Object value);

	/**
	 * 缓存数据,可控制数据有效时间
	 * 
	 * @param key
	 * @param value
	 * @param expiry
	 */
	public void putValue(Object key,Object value,long expiry);

	/**
	 * 移除指定key的缓存信息
	 * 
	 * @param key
	 */
	public void evict(Object key);

	/**
	 * 清除全部缓存信息
	 */
	public void clear();

}