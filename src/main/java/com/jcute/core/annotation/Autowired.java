package com.jcute.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 标记需要注入的field,method,parameter
 * 
 * @author koko
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD,ElementType.METHOD,ElementType.PARAMETER})
public @interface Autowired{

	/**
	 * 多个实例时需要手动指定名称
	 * 
	 * @return bean名称
	 */
	public String value() default "";

}