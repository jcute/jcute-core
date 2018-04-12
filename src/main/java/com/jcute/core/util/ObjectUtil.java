package com.jcute.core.util;

import java.util.Arrays;

/**
 * 对象处理工具类
 * 
 * @author tangbin
 */
public class ObjectUtil{

	/**
	 * 判断数组是否相等
	 * 
	 * @param objectA
	 * @param objectB
	 * @return 返回是否一致
	 */
	public static boolean arrayEquals(Object objectA,Object objectB){
		if(objectA instanceof Object[] && objectB instanceof Object[]){
			return Arrays.equals((Object[])objectA,(Object[])objectB);
		}
		if(objectA instanceof boolean[] && objectB instanceof boolean[]){
			return Arrays.equals((boolean[])objectA,(boolean[])objectB);
		}
		if(objectA instanceof byte[] && objectB instanceof byte[]){
			return Arrays.equals((byte[])objectA,(byte[])objectB);
		}
		if(objectA instanceof char[] && objectB instanceof char[]){
			return Arrays.equals((char[])objectA,(char[])objectB);
		}
		if(objectA instanceof double[] && objectB instanceof double[]){
			return Arrays.equals((double[])objectA,(double[])objectB);
		}
		if(objectA instanceof float[] && objectB instanceof float[]){
			return Arrays.equals((float[])objectA,(float[])objectB);
		}
		if(objectA instanceof int[] && objectB instanceof int[]){
			return Arrays.equals((int[])objectA,(int[])objectB);
		}
		if(objectA instanceof long[] && objectB instanceof long[]){
			return Arrays.equals((long[])objectA,(long[])objectB);
		}
		if(objectA instanceof short[] && objectB instanceof short[]){
			return Arrays.equals((short[])objectA,(short[])objectB);
		}
		return false;
	}

	/**
	 * 判断对象是否相同,参数可为空
	 * 
	 * @param objectA
	 * @param objectB
	 * @return 返回是否一致
	 */
	public static boolean nullSafeEquals(Object objectA,Object objectB){
		if(objectA == objectB){
			return true;
		}
		if(null == objectA || null == objectB){
			return false;
		}
		if(objectA.equals(objectB)){
			return true;
		}
		if(objectA.getClass().isArray() && objectB.getClass().isArray()){
			return arrayEquals(objectA,objectB);
		}
		return false;
	}

	/**
	 * 获取对象的hascode,参数可为空
	 * 
	 * @param object
	 * @return 返回hasCode
	 */
	public static int nullSafeHashCode(Object object){
		if(object == null){
			return 0;
		}
		if(object.getClass().isArray()){
			if(object instanceof Object[]){
				return nullSafeHashCode((Object[])object);
			}
			if(object instanceof boolean[]){
				return nullSafeHashCode((boolean[])object);
			}
			if(object instanceof byte[]){
				return nullSafeHashCode((byte[])object);
			}
			if(object instanceof char[]){
				return nullSafeHashCode((char[])object);
			}
			if(object instanceof double[]){
				return nullSafeHashCode((double[])object);
			}
			if(object instanceof float[]){
				return nullSafeHashCode((float[])object);
			}
			if(object instanceof int[]){
				return nullSafeHashCode((int[])object);
			}
			if(object instanceof long[]){
				return nullSafeHashCode((long[])object);
			}
			if(object instanceof short[]){
				return nullSafeHashCode((short[])object);
			}
		}
		return object.hashCode();
	}

}
