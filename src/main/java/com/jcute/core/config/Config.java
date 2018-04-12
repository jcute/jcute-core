package com.jcute.core.config;

import java.math.BigDecimal;

/**
 * 属性读取配置
 * 
 * @author koko
 *
 */
public interface Config{

	public Integer getIntegerValue(String name,Integer defaultValue);

	public Integer getIntegerValue(String name);

	public Long getLongValue(String name,Long defaultValue);

	public Long getLongValue(String name);

	public Short getShortValue(String name,Short defaultValue);

	public Short getShortValue(String name);

	public Double getDoubleValue(String name,Double defaultValue);

	public Double getDoubleValue(String name);

	public Float getFloatValue(String name,Float defaultValue);

	public Float getFloatValue(String name);

	public Boolean getBooleanValue(String name,Boolean defaultValue);

	public Boolean getBooleanValue(String name);

	public String getStringValue(String name,String defaultValue);

	public String getStringValue(String name);

	public BigDecimal getBigDecimalValue(String name,BigDecimal defaultValue);

	public BigDecimal getBigDecimalValue(String name);
	
	public <T> T getValue(String name,Class<T> type,String defaultValue);

	public boolean containsName(String name);
	
}