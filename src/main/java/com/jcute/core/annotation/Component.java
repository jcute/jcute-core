package com.jcute.core.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.jcute.core.bean.BeanScope;

/**
 * 标记需要实例化的Bean
 * 
 * @author koko
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE,ElementType.METHOD})
@Documented
public @interface Component{

	/**
	 * 组件名称
	 * 
	 * @return 默认为空
	 */
	public String value() default "";

	/**
	 * 组件作用域
	 * 
	 * @return 默认为Singleton
	 */
	public BeanScope scope() default BeanScope.Singleton;

}