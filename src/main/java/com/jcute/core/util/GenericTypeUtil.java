package com.jcute.core.util;

import java.util.Map;

/**
 * 范型处理工具类
 * 
 * @author tangbin
 */
public class GenericTypeUtil{

	/**
	 * 泛型类型转换,避免直接转换时出现unchecked
	 * 
	 * @param object
	 * @return 返回泛型类型
	 */
	@SuppressWarnings("unchecked")
	public static <T> T parseType(Object object){
		return (T)object;
	}

	@SuppressWarnings("unchecked")
	public static <K,V> Map<K,V> parseType(Map<?,?> object){
		return (Map<K,V>)object;
	}

}
