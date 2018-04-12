package com.jcute.core.config.abs;

import java.math.BigDecimal;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import com.jcute.core.config.Config;
import com.jcute.core.config.ConfigManager;
import com.jcute.core.config.ConfigType;
import com.jcute.core.config.imp.ConfigByEnvironment;
import com.jcute.core.config.imp.ConfigByProperties;
import com.jcute.core.config.imp.ConfigBySystemProperties;
import com.jcute.core.resource.Resource;
import com.jcute.core.util.GenericTypeUtil;
import com.jcute.core.util.StringUtil;

public abstract class AbstractConfigManager implements ConfigManager{

	private static int defaultNullIntValue;
	private static short defaultNullShortValue;
	private static long defaultNullLongValue;
	private static double defaultNullDoubleValue;
	private static float defaultNullFloatValue;
	private static boolean defaultNullBooleanValue;

	private Set<Config> configs = new LinkedHashSet<Config>();
	private Map<ConfigName,Object> caches = new ConcurrentHashMap<ConfigName,Object>();

	public AbstractConfigManager(){
		this.configs.add(new ConfigByEnvironment());
		this.configs.add(new ConfigBySystemProperties());
	}

	@Override
	public void addConfig(Config config){
		if(null == config){
			return;
		}
		this.configs.add(config);
	}

	@Override
	public void addConfig(Resource resource,ConfigType configType){
		if(null == resource || null == configType){
			return;
		}
		if(configType == ConfigType.Properties){
			Properties properties = configType.convert(resource);
			this.addConfig(new ConfigByProperties(properties));
		}
	}

	@Override
	public Set<Config> getAllConfigs(){
		return this.configs;
	}

	@Override
	public <T> T getConfigValue(String configName,Class<T> type){
		if(StringUtil.isBlank(configName) || configName.trim().equals(":")){
			return null;
		}
		ConfigName name = ConfigName.create(configName);
		if(this.caches.containsKey(name)){
			return GenericTypeUtil.parseType(this.caches.get(name));
		}
		T result = null;
		for(Config config : this.configs){
			if(config.containsName(name.getRelName())){
				result = config.getValue(name.getRelName(),type,name.getDefValue());
			}
			if(null != result){
				break;
			}
		}
		if(null == result){
			return this.getDefaultValue(type,name.getDefValue());
		}else{
			this.caches.put(name,result);
		}
		return result;
	}

	private <T> T getDefaultValue(Class<T> type,String defaultValue){
		T result = null;
		if(null == defaultValue){
			if(int.class.equals(type)){
				result = GenericTypeUtil.parseType(defaultNullIntValue);
			}else if(short.class.equals(type)){
				result = GenericTypeUtil.parseType(defaultNullShortValue);
			}else if(long.class.equals(type)){
				result = GenericTypeUtil.parseType(defaultNullLongValue);
			}else if(double.class.equals(type)){
				result = GenericTypeUtil.parseType(defaultNullDoubleValue);
			}else if(float.class.equals(type)){
				result = GenericTypeUtil.parseType(defaultNullFloatValue);
			}else if(boolean.class.equals(type)){
				result = GenericTypeUtil.parseType(defaultNullBooleanValue);
			}else if(String.class.equals(type)){
				result = null;
			}else if(BigDecimal.class.equals(type)){
				result = null;
			}
		}else{
			if(int.class.equals(type) || Integer.class.equals(type)){
				try{
					result = GenericTypeUtil.parseType(Integer.valueOf(defaultValue));
				}catch(Exception e){
					if(int.class.equals(type)){
						result = GenericTypeUtil.parseType(defaultNullIntValue);
					}
				}
			}else if(short.class.equals(type) || Short.class.equals(type)){
				try{
					result = GenericTypeUtil.parseType(Short.valueOf(defaultValue));
				}catch(Exception e){
					if(short.class.equals(type)){
						result = GenericTypeUtil.parseType(defaultNullShortValue);
					}
				}
			}else if(long.class.equals(type) || Long.class.equals(type)){
				try{
					result = GenericTypeUtil.parseType(Long.valueOf(defaultValue));
				}catch(Exception e){
					if(long.class.equals(type)){
						result = GenericTypeUtil.parseType(defaultNullLongValue);
					}
				}
			}else if(double.class.equals(type) || Double.class.equals(type)){
				try{
					result = GenericTypeUtil.parseType(Double.valueOf(defaultValue));
				}catch(Exception e){
					if(double.class.equals(type)){
						result = GenericTypeUtil.parseType(defaultNullDoubleValue);
					}
				}
			}else if(float.class.equals(type) || Float.class.equals(type)){
				try{
					result = GenericTypeUtil.parseType(Float.valueOf(defaultValue));
				}catch(Exception e){
					if(float.class.equals(type)){
						result = GenericTypeUtil.parseType(defaultNullFloatValue);
					}
				}
			}else if(boolean.class.equals(type) || Boolean.class.equals(type)){
				try{
					result = GenericTypeUtil.parseType(Boolean.valueOf(defaultValue));
				}catch(Exception e){
					if(boolean.class.equals(type)){
						result = GenericTypeUtil.parseType(defaultNullBooleanValue);
					}
				}
			}else if(String.class.equals(type)){
				result = GenericTypeUtil.parseType(defaultValue);
			}else if(BigDecimal.class.equals(type)){
				result = GenericTypeUtil.parseType(new BigDecimal(0));
			}
		}
		return result;
	}

	private static class ConfigName{

		public static ConfigName create(String configName){
			return new ConfigName(configName);
		}

		private String rawName;
		private String relName;
		private String defValue;

		private ConfigName(String rawName){
			this.rawName = rawName;
			this.resolve();
		}

		private void resolve(){
			if(this.rawName.indexOf(":") > 0){
				String[] temp = this.rawName.split(":");
				this.relName = temp[0].trim();
				this.defValue = temp[1].trim();
			}else{
				this.relName = this.rawName;
				this.defValue = null;
			}
		}

		private String getRelName(){
			return this.relName;
		}

		private String getDefValue(){
			if(StringUtil.isBlank(this.defValue)){
				return null;
			}
			return this.defValue;
		}

		@Override
		public int hashCode(){
			return this.toString().hashCode();
		}

		@Override
		public boolean equals(Object obj){
			if(null == obj){
				return false;
			}
			if(obj instanceof ConfigName){
				return obj.toString().equals(this.toString());
			}
			return false;
		}

		@Override
		public String toString(){
			return this.rawName;
		}

	}

}