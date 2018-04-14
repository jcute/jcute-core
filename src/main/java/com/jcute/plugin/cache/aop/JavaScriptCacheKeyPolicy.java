package com.jcute.plugin.cache.aop;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import com.jcute.plugin.cache.CacheKeyPolicy;
import com.jcute.plugin.cache.abs.AbstractCacheKeyPolicy;

public class JavaScriptCacheKeyPolicy extends AbstractCacheKeyPolicy{
	
	private ScriptEngineManager manager = new ScriptEngineManager();
	private ScriptEngine engine = manager.getEngineByName("js");
	
	@Override
	protected Object doGetCacheKey(Map<String,Object> context,String expression){
		try{
			StringBuffer stringBuffer = new StringBuffer();
			stringBuffer.append("var getCacheKey = function(");
			int index = 0;
			Object[] datas = new Object[context.size()];
			for(Entry<String,Object> entry : context.entrySet()){
				stringBuffer.append(entry.getKey());
				datas[index] = entry.getValue();
				if(++index!=context.size()){
					stringBuffer.append(",");
				}
			}
			stringBuffer.append("){");
			stringBuffer.append("return").append(" ").append(expression).append(";");
			stringBuffer.append("}");
			this.engine.eval(stringBuffer.toString());
			Invocable invocable = (Invocable)engine;
			return invocable.invokeFunction("getCacheKey",datas);
		}catch(Exception e){
			return null;
		}
	}
	
	public static void main(String[] args){
		CacheKeyPolicy cacheKeyPolicy = new JavaScriptCacheKeyPolicy();
		
		Map<String,Object> context = new HashMap<String,Object>();
		context.put("name","test");
		String expression = "name + 'hello'";
		
		System.out.println(cacheKeyPolicy.getCacheKey(context,expression));
		long a = System.currentTimeMillis();
		System.out.println(cacheKeyPolicy.getCacheKey(context,expression));
		System.out.println(System.currentTimeMillis() - a);
		
	}
	
}