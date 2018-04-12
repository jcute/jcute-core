package com.jcute.core.scan;

/**
 * class的扫描过滤器
 */
public interface PackageScannerFilter{

	public boolean doFilter(Class<?> target);

}