package com.jcute.plugin.shutdown;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.jcute.core.annotation.Pluggable;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Pluggable(EnableShutDownHookPlugin.class)
public @interface EnableShutDownHook{
	
	
	
}