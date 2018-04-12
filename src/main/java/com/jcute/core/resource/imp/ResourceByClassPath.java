package com.jcute.core.resource.imp;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import com.jcute.core.resource.abs.AbstractResource;
import com.jcute.core.util.ClassUtil;
import com.jcute.core.util.ResourceUtil;
import com.jcute.core.util.StringUtil;

public class ResourceByClassPath extends AbstractResource{

	private String path;
	private ClassLoader classLoader;

	public ResourceByClassPath(String path,ClassLoader classLoader){
		if(StringUtil.isBlank(path)){
			throw new IllegalArgumentException("Path must not be null");
		}
		this.path = ResourceUtil.cleanPath(path);
		this.classLoader = classLoader == null ? ClassUtil.getDefaultClassLoader() : classLoader;
	}

	@Override
	public String getFileName(){
		return ResourceUtil.getFileName(this.path);
	}

	@Override
	public String getFilePath(){
		return this.path;
	}

	@Override
	public InputStream getFileInputStream() throws IOException{
		InputStream inputStream = null;
		if(null != this.classLoader){
			inputStream = this.classLoader.getResourceAsStream(this.path);
		}else{
			inputStream = ClassLoader.getSystemResourceAsStream(this.path);
		}
		if(null == inputStream){
			throw new FileNotFoundException(String.format("Resource cannot be opened because it does not exist [%s]",this.path));
		}
		return inputStream;
	}

}