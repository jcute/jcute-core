package com.jcute.core.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 包扫描注解,value直接输入包使用通配符<br/>
 * 例：<br/>
 * com.*.demo 一级目录包匹配<br/>
 * com.**.demo 多级目录包匹配<br/>
 * com.?remoter.demo 单子母目录包匹配<br/>
 * 
 * @author tangbin
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE,ElementType.ANNOTATION_TYPE})
@Documented
public @interface ComponentScan{

	public String[] value();

}