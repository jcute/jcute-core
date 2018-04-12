package com.jcute.core.scan;

import java.util.Set;

/**
 * 包扫描器
 */
public interface PackageScanner{

	/**
	 * 添加扫描的包或通配符
	 * 
	 * @param pattern
	 */
	public void addPattern(String pattern);

	/**
	 * 添加扫描的包或通配符
	 * 
	 * @param pattern
	 */
	public void addPattern(Set<String> patterns);

	/**
	 * 添加扫描过滤器,可通过过滤器控制package或class
	 * 
	 * @param packageScannerFilter
	 */
	public void addPackageScannerFilter(PackageScannerFilter packageScannerFilter);

	/**
	 * 执行扫描操作,返回所有扫描结果的集合
	 * 
	 * @return 返回扫描结果结合,为空或具体数据,不为null
	 */
	public Set<Class<?>> scan();

}