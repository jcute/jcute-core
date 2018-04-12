package com.jcute.core.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * 注解处理工具类
 * 
 * @author tangbin
 */
public class AnnotationUtil{

	/**
	 * 判断构造函数是否包含指定注解
	 * 
	 * @param constructor
	 * @param annotation
	 * @return true为存在
	 */
	public static boolean hasAnnotation(Constructor<?> constructor,Class<? extends Annotation> annotation){
		return null != constructor.getAnnotation(annotation);
	}

	public static boolean hasAnnotation(Method method,Class<? extends Annotation> annotation){
		return null != method.getAnnotation(annotation);
	}

	public static boolean hasAnnotation(Field field,Class<? extends Annotation> annotation){
		return null != field.getAnnotation(annotation);
	}

	public static boolean hasAnnotation(Class<?> clazz,Class<? extends Annotation> annotation){
		return null != clazz.getAnnotation(annotation);
	}

	public static boolean hasAnnotation(Annotation[][] annotations,Class<? extends Annotation> annotation){
		if(null == annotations || annotations.length == 0){
			return false;
		}
		for(int i = 0;i < annotations.length;i++){
			Annotation[] ans = annotations[i];
			if(null == ans || ans.length == 0){
				continue;
			}
			for(int j = 0;j < ans.length;j++){
				Annotation an = ans[j];
				if(an.annotationType().equals(annotation)){
					return true;
				}
			}
		}
		return false;
	}

	public static <T extends Annotation> T getAnnotation(Method method,Class<T> annotation){
		return method.getAnnotation(annotation);
	}

	public static <T extends Annotation> T getAnnotation(Field field,Class<T> annotation){
		return field.getAnnotation(annotation);
	}

	public static <T extends Annotation> T getAnnotation(Class<?> clazz,Class<T> annotation){
		return clazz.getAnnotation(annotation);
	}

	public static Annotation[] getAnnotation(Class<?>[] parameterTypes,Annotation[][] annotations,Class<? extends Annotation> annotation){
		if(null == parameterTypes || parameterTypes.length == 0){
			return new Annotation[0];
		}
		Annotation[] list = new Annotation[parameterTypes.length];
		if(null == parameterTypes || parameterTypes.length == 0){
			return list;
		}
		for(int i = 0;i < parameterTypes.length;i++){
			Annotation[] ans = annotations[i];
			Annotation an = null;
			for(Annotation a : ans){
				if(a.annotationType().equals(annotation)){
					an = a;
					break;
				}
			}
			list[i] = an;
		}
		return list;
	}

}
