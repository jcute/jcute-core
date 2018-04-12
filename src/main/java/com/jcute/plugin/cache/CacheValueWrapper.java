package com.jcute.plugin.cache;

import java.io.Serializable;

/**
 * 缓存值，持有器
 * 
 * @author tangbin
 *
 */
public interface CacheValueWrapper extends Serializable{

	/**
	 * @return 返回持有的对象
	 */
	public Object getValue();

}