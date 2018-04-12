package com.jcute.core.resource.imp;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;

import com.jcute.core.resource.abs.AbstractResource;
import com.jcute.core.util.ResourceUtil;

public class ResourceByUrl extends AbstractResource{

	private final URI uri;
	private final URL url;
	private final URL cleanedUrl;

	public ResourceByUrl(URI uri) throws MalformedURLException{
		if(null == uri){
			throw new IllegalArgumentException("URI must not be null");
		}
		this.uri = uri;
		this.url = uri.toURL();
		this.cleanedUrl = this.getCleanURL(this.url,uri.toString());
	}

	public ResourceByUrl(URL url){
		if(null == url){
			throw new IllegalArgumentException("URL must not be null");
		}
		this.uri = null;
		this.url = url;
		this.cleanedUrl = this.getCleanURL(this.url,url.toString());
	}

	public ResourceByUrl(String path) throws MalformedURLException{
		if(null == path){
			throw new IllegalArgumentException("Path must not be null");
		}
		this.uri = null;
		this.url = new URL(path);
		this.cleanedUrl = this.getCleanURL(this.url,path);
	}

	public ResourceByUrl(String protocol,String location,String fragment) throws MalformedURLException{
		try{
			this.uri = new URI(protocol,location,fragment);
			this.url = this.uri.toURL();
			this.cleanedUrl = this.getCleanURL(this.url,this.uri.toString());
		}catch(URISyntaxException e){
			MalformedURLException me = new MalformedURLException(e.getMessage());
			me.initCause(e);
			throw me;
		}
	}

	public ResourceByUrl(String protocol,String location) throws MalformedURLException{
		this(protocol,location,null);
	}

	@Override
	public String getFileName(){
		return ResourceUtil.getFileName(this.cleanedUrl.getPath());
	}

	@Override
	public String getFilePath(){
		return this.cleanedUrl.getFile();
	}

	@Override
	public InputStream getFileInputStream() throws IOException{
		URLConnection urlConnection = this.url.openConnection();
		ResourceUtil.useCachesIfNecessary(urlConnection);
		try{
			return urlConnection.getInputStream();
		}catch(IOException e){
			if(urlConnection instanceof HttpURLConnection){
				((HttpURLConnection)urlConnection).disconnect();
			}
			throw e;
		}
	}

	private URL getCleanURL(URL originalUrl,String originalPath){
		try{
			return new URL(ResourceUtil.cleanPath(originalPath));
		}catch(MalformedURLException e){
			return originalUrl;
		}
	}

}