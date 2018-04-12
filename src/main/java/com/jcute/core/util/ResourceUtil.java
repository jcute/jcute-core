package com.jcute.core.util;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.LinkedList;
import java.util.List;

import com.jcute.core.resource.Resource;
import com.jcute.core.resource.imp.ResourceByClassPath;
import com.jcute.core.resource.imp.ResourceByUrl;

public class ResourceUtil{

	public static final String FOLDER_SEPARATOR = "/";
	public static final String WINDOWS_FOLDER_SEPARATOR = "\\";
	public static final String TOP_PATH = "..";
	public static final String CURRENT_PATH = ".";

	public static final String CLASSPATH_URL_PREFIX = "classpath:";

	/**
	 * 获取指定的资源文件对象
	 * 
	 * @param location
	 * @param classLoader
	 * @return 返回资源文件对象
	 */
	public static Resource getResource(String location,ClassLoader classLoader){
		ClassLoader newClassLoader = null == classLoader ? ClassUtil.getDefaultClassLoader() : classLoader;
		if(null == location){
			throw new IllegalArgumentException("Location must be not null");
		}
		if(location.startsWith("/")){
			return new ResourceByClassPath(location.substring(1),newClassLoader);
		}else if(location.startsWith(CLASSPATH_URL_PREFIX)){
			return new ResourceByClassPath(location.substring(CLASSPATH_URL_PREFIX.length()),newClassLoader);
		}else{
			try{
				URL url = new URL(location);
				return new ResourceByUrl(url);
			}catch(MalformedURLException e){
				return new ResourceByClassPath(location,newClassLoader);
			}
		}
	}

	public static Resource getResource(String location){
		return getResource(location,null);
	}

	/**
	 * 路径清洗
	 * 
	 * @param path
	 * @return 返回清洗结果
	 */
	public static String cleanPath(String path){
		if(StringUtil.isBlank(path)){
			return path;
		}
		String pathToUse = StringUtil.replace(path,WINDOWS_FOLDER_SEPARATOR,FOLDER_SEPARATOR);
		int prefixIndex = pathToUse.indexOf(":");
		String prefix = "";
		if(prefixIndex != -1){
			prefix = pathToUse.substring(0,prefixIndex + 1);
			if(prefix.contains("/")){
				prefix = "";
			}else{
				pathToUse = pathToUse.substring(prefixIndex + 1);
			}
		}
		if(pathToUse.startsWith(FOLDER_SEPARATOR)){
			prefix = prefix + FOLDER_SEPARATOR;
			pathToUse = pathToUse.substring(1);
		}
		String[] pathArray = StringUtil.delimitedListToStringArray(pathToUse,FOLDER_SEPARATOR);
		List<String> pathElements = new LinkedList<String>();
		int tops = 0;
		for(int i = pathArray.length - 1;i >= 0;i--){
			String element = pathArray[i];
			if(CURRENT_PATH.equals(element)){}else if(TOP_PATH.equals(element)){
				tops++;
			}else{
				if(tops > 0){
					tops--;
				}else{
					pathElements.add(0,element);
				}
			}
		}
		for(int i = 0;i < tops;i++){
			pathElements.add(0,TOP_PATH);
		}
		return prefix + StringUtil.collectionToDelimitedString(pathElements,FOLDER_SEPARATOR);
	}

	/**
	 * 获取给定路径的文件名称
	 * 
	 * @param path
	 * @return 返回文件名称
	 */
	public static String getFileName(String path){
		if(StringUtil.isBlank(path)){
			return null;
		}
		int separatorIndex = path.lastIndexOf(FOLDER_SEPARATOR);
		return separatorIndex == -1 ? path : path.substring(separatorIndex + 1);
	}

	/**
	 * 判断URLConnection 类名以JNLP开头则设置UseCaches JNLP（Java Network Launching Protocol） 是java提供的一种可以通过浏览器直接执行java应用程序的途径， 它使你可以直接通过一个网页上的url连接打开一个java应用程序。
	 * 
	 * @param con
	 */
	public static void useCachesIfNecessary(URLConnection con){
		con.setUseCaches(con.getClass().getSimpleName().startsWith("JNLP"));
	}

}