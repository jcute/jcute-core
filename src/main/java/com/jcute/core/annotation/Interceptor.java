package com.jcute.core.annotation;

import java.lang.annotation.Annotation;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
public @interface Interceptor{

	/**
	 * 拦截的包信息,或类全称,兼容AntPathMatcher,可使用通配符
	 * 
	 * @return
	 */
	public String[] packages() default {};

	/**
	 * 拦截的注解,如果两个参数都设置则为且条件
	 * 
	 * @return
	 */
	public Class<? extends Annotation>[] annotations() default {};

}