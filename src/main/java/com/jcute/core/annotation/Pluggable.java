package com.jcute.core.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.jcute.core.plugin.Plugin;

/**
 * 实现类似spring boot的注解插件机制
 * 
 * @author tangbin
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.ANNOTATION_TYPE})
@Documented
public @interface Pluggable{

	/**
	 * 用来指定插件的实现类
	 * 
	 * @return 返回插件的实现类
	 */
	public Class<? extends Plugin> value();

	/**
	 * 插件执行顺序
	 * 
	 * @return 返回用户指定的顺序
	 */
	public int order() default Integer.MAX_VALUE;

}