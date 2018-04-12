package com.jcute.core.resource;

import java.io.IOException;
import java.io.InputStream;

/**
 * 资源读取封装接口
 */
public interface Resource{

	/**
	 * 获取资源文件名称
	 * 
	 * @return 返回文件名称
	 */
	public String getFileName();

	/**
	 * 获取资源路径
	 * 
	 * @return
	 */
	public String getFilePath();

	/**
	 * 判断资源是否存在
	 * 
	 * @return
	 */
	public boolean isExists();

	/**
	 * 获取资源文件长度
	 * 
	 * @return
	 */
	public long getFileLength() throws IOException;

	/**
	 * 获取资源输入流
	 * 
	 * @return
	 */
	public InputStream getFileInputStream() throws IOException;

}