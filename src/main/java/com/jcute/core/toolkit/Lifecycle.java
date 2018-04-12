package com.jcute.core.toolkit;

import com.jcute.core.error.ContextCreateException;
import com.jcute.core.error.ContextDestoryException;
import com.jcute.core.error.ContextInitialException;
import com.jcute.core.error.ContextInjectException;

/**
 * 框架生命周期
 * 
 * @author koko
 *
 */
public interface Lifecycle{

	/**
	 * 初始化
	 */
	public void initial() throws ContextInitialException;

	/**
	 * 销毁
	 */
	public void destory() throws ContextDestoryException;

	/**
	 * 创建
	 */
	public void create() throws ContextCreateException;

	/**
	 * 注入
	 */
	public void inject() throws ContextInjectException;

}