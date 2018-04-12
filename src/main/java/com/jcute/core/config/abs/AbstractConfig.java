package com.jcute.core.config.abs;

import java.math.BigDecimal;

import com.jcute.core.config.Config;
import com.jcute.core.util.GenericTypeUtil;
import com.jcute.core.util.StringUtil;

public abstract class AbstractConfig implements Config{

	@Override
	public Integer getIntegerValue(String name,Integer defaultValue){
		if(StringUtil.isBlank(name)){
			return defaultValue;
		}
		String result = this.getValue(name);
		if(null == result){
			return defaultValue;
		}
		try{
			return Integer.valueOf(result);
		}catch(Exception e){
			return defaultValue;
		}
	}

	@Override
	public Integer getIntegerValue(String name){
		return this.getIntegerValue(name,null);
	}

	@Override
	public Long getLongValue(String name,Long defaultValue){
		if(StringUtil.isBlank(name)){
			return defaultValue;
		}
		String result = this.getValue(name);
		if(null == result){
			return defaultValue;
		}
		try{
			return Long.valueOf(result);
		}catch(Exception e){
			return defaultValue;
		}
	}

	@Override
	public Long getLongValue(String name){
		return this.getLongValue(name,null);
	}

	@Override
	public Short getShortValue(String name,Short defaultValue){
		if(StringUtil.isBlank(name)){
			return defaultValue;
		}
		String result = this.getValue(name);
		if(null == result){
			return defaultValue;
		}
		try{
			return Short.valueOf(result);
		}catch(Exception e){
			return defaultValue;
		}
	}

	@Override
	public Short getShortValue(String name){
		return this.getShortValue(name,null);
	}

	@Override
	public Double getDoubleValue(String name,Double defaultValue){
		if(StringUtil.isBlank(name)){
			return defaultValue;
		}
		String result = this.getValue(name);
		if(null == result){
			return defaultValue;
		}
		try{
			return Double.valueOf(result);
		}catch(Exception e){
			return defaultValue;
		}
	}

	@Override
	public Double getDoubleValue(String name){
		return this.getDoubleValue(name,null);
	}

	@Override
	public Float getFloatValue(String name,Float defaultValue){
		if(StringUtil.isBlank(name)){
			return defaultValue;
		}
		String result = this.getValue(name);
		if(null == result){
			return defaultValue;
		}
		try{
			return Float.valueOf(result);
		}catch(Exception e){
			return defaultValue;
		}
	}

	@Override
	public Float getFloatValue(String name){
		return this.getFloatValue(name,null);
	}

	@Override
	public Boolean getBooleanValue(String name,Boolean defaultValue){
		if(StringUtil.isBlank(name)){
			return defaultValue;
		}
		String result = this.getValue(name);
		if(null == result){
			return defaultValue;
		}
		try{
			return Boolean.valueOf(result);
		}catch(Exception e){
			return defaultValue;
		}
	}

	@Override
	public Boolean getBooleanValue(String name){
		return this.getBooleanValue(name,null);
	}

	@Override
	public String getStringValue(String name,String defaultValue){
		if(StringUtil.isBlank(name)){
			return defaultValue;
		}
		String result = this.getValue(name);
		if(null == result){
			return defaultValue;
		}
		return result;
	}

	@Override
	public String getStringValue(String name){
		return this.getStringValue(name,null);
	}

	@Override
	public BigDecimal getBigDecimalValue(String name,BigDecimal defaultValue){
		if(StringUtil.isBlank(name)){
			return defaultValue;
		}
		String result = this.getValue(name);
		if(null == result){
			return defaultValue;
		}
		try{
			return new BigDecimal(result);
		}catch(Exception e){
			return defaultValue;
		}
	}

	@Override
	public BigDecimal getBigDecimalValue(String name){
		return this.getBigDecimalValue(name,null);
	}

	@Override
	public <T> T getValue(String name,Class<T> type,String defaultValue){
		try{
			if(type.equals(Integer.class) || type.equals(int.class)){
				return GenericTypeUtil.parseType(this.getIntegerValue(name));
			}
			if(type.equals(Long.class) || type.equals(long.class)){
				return GenericTypeUtil.parseType(this.getLongValue(name));
			}
			if(type.equals(Short.class) || type.equals(short.class)){
				return GenericTypeUtil.parseType(this.getShortValue(name));
			}
			if(type.equals(Double.class) || type.equals(double.class)){
				return GenericTypeUtil.parseType(this.getDoubleValue(name));
			}
			if(type.equals(Float.class) || type.equals(float.class)){
				return GenericTypeUtil.parseType(this.getFloatValue(name));
			}
			if(type.equals(Boolean.class) || type.equals(boolean.class)){
				return GenericTypeUtil.parseType(this.getBooleanValue(name));
			}
			if(type.equals(String.class)){
				return GenericTypeUtil.parseType(this.getStringValue(name));
			}
			if(type.equals(BigDecimal.class)){
				return GenericTypeUtil.parseType(this.getBigDecimalValue(name));
			}
		}catch(Exception e){
			return null;
		}
		return null;
	}

	protected abstract String getValue(String name);

}