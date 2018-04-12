package com.jcute.core.plugin.abs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.jcute.core.annotation.Order;
import com.jcute.core.error.ContextCreateException;
import com.jcute.core.error.ContextDestoryException;
import com.jcute.core.error.ContextInitialException;
import com.jcute.core.error.ContextInjectException;
import com.jcute.core.plugin.Plugin;
import com.jcute.core.plugin.PluginManager;
import com.jcute.core.scan.PackageScanner;
import com.jcute.core.util.AnnotationUtil;

public abstract class AbstractPluginManager implements PluginManager,Comparator<Plugin>{

	private List<Plugin> plugins = new ArrayList<Plugin>();
	
	@Override
	public void addPlugin(Plugin plugin){
		if(null == plugin){
			throw new IllegalArgumentException("Plugin must not be null");
		}
		if(this.plugins.contains(plugin)){
			return;
		}
		this.plugins.add(plugin);
		Collections.sort(this.plugins,this);
	}

	@Override
	public List<Plugin> getAllPlugins(){
		return Collections.unmodifiableList(this.plugins);
	}

	@Override
	public void onBeforeScanning(PackageScanner packageScanner) throws ContextInitialException{
		for(Plugin plugin:this.getAllPlugins()){
			plugin.onBeforeScanning(packageScanner);
		}
	}

	@Override
	public void onBeforeInitial() throws ContextInitialException{
		for(Plugin plugin:this.getAllPlugins()){
			plugin.onBeforeInitial();
		}
	}

	@Override
	public void onBeforeCreate() throws ContextCreateException{
		for(Plugin plugin:this.getAllPlugins()){
			plugin.onBeforeCreate();
		}
	}

	@Override
	public void onBeforeInject() throws ContextInjectException{
		for(Plugin plugin:this.getAllPlugins()){
			plugin.onBeforeInject();
		}
	}

	@Override
	public void onAfterInject() throws ContextInjectException{
		for(Plugin plugin:this.getAllPlugins()){
			plugin.onAfterInject();
		}
	}

	@Override
	public void onBeforeDestory() throws ContextDestoryException{
		for(Plugin plugin:this.getAllPlugins()){
			plugin.onBeforeDestory();
		}
	}

	@Override
	public int compare(Plugin o1,Plugin o2){
		Class<?> c1 = o1.getClass();
		Class<?> c2 = o2.getClass();
		int p1 = 0;
		int p2 = 0;
		if(AnnotationUtil.hasAnnotation(c1,Order.class)){
			p1 = AnnotationUtil.getAnnotation(c1,Order.class).value();
		}
		if(AnnotationUtil.hasAnnotation(c2,Order.class)){
			p2 = AnnotationUtil.getAnnotation(c2,Order.class).value();
		}
		return p1 - p2;
	}

}