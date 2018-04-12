package com.jcute.plugin.cache;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface CachePut{

	// key
	public String cacheKey();

	// 有效期,0为长期有效
	public long cacheExpiry() default 0L;

	public String cacheName() default "";

	// cacheManager名称
	public String cacheManager() default "";

}