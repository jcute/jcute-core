package com.jcute.core.plugin;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Map;

import com.jcute.core.error.ContextCreateException;
import com.jcute.core.error.ContextDestoryException;
import com.jcute.core.error.ContextInitialException;
import com.jcute.core.error.ContextInjectException;
import com.jcute.core.scan.PackageScanner;

/**
 * 插件管理器
 * 
 * @author tangbin
 *
 */
public interface PluginManager{

	/**
	 * 注册插件
	 * 
	 * @param plugin
	 */
	public void addPlugin(Plugin plugin);

	/**
	 * 返回所有插件
	 * 
	 * @return
	 */
	public List<Plugin> getAllPlugins();

	/**
	 * 查询指定class的插件信息
	 * 
	 * @param target
	 * @return
	 */
	public Map<Annotation,Class<? extends Plugin>> searchPlugins(Class<?> target);

	public void onBeforeScanning(PackageScanner packageScanner) throws ContextInitialException;

	public void onBeforeInitial() throws ContextInitialException;

	public void onBeforeCreate() throws ContextCreateException;

	public void onBeforeInject() throws ContextInjectException;

	public void onAfterInject() throws ContextInjectException;

	public void onBeforeDestory() throws ContextDestoryException;

}