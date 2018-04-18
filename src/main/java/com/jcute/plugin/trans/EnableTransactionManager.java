package com.jcute.plugin.trans;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.jcute.core.annotation.Pluggable;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Pluggable(EnableTransactionManagerPlugin.class)
@Documented
public @interface EnableTransactionManager{
	
	
	
}