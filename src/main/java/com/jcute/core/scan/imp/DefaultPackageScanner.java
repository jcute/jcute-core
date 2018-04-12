package com.jcute.core.scan.imp;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.Collections;
import java.util.Enumeration;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import com.jcute.core.scan.abs.AbstractPackageScanner;
import com.jcute.core.util.ClassUtil;
import com.jcute.core.util.StringUtil;

/**
 * 执行包扫描操作
 */
public class DefaultPackageScanner extends AbstractPackageScanner implements FileFilter{

	private ClassLoader classLoader;

	public DefaultPackageScanner(ClassLoader classLoader){
		if(null == classLoader){
			this.classLoader = ClassUtil.getDefaultClassLoader();
		}else{
			this.classLoader = classLoader;
		}
	}

	public DefaultPackageScanner(){
		this(null);
	}

	/**
	 * 执行扫描操作,获取所有可扫描的路径,进行扫描
	 */
	@Override
	protected Set<Class<?>> doScan(){
		Set<String> paths = this.getScanPaths();
		Set<Class<?>> classes = new LinkedHashSet<Class<?>>();
		if(null != paths && paths.size() > 0){
			for(String path : paths){
				classes.addAll(this.doScan(path));
			}
		}
		return Collections.unmodifiableSet(classes);
	}

	private Set<Class<?>> doScan(String path){
		Set<Class<?>> classes = new LinkedHashSet<Class<?>>();
		Enumeration<URL> urls = null;
		try{
			urls = this.classLoader.getResources(path);
			if(null != urls){
				while(urls.hasMoreElements()){
					URL url = urls.nextElement();
					String protocol = url.getProtocol();
					if("file".equals(protocol)){
						this.doSearchForDir(classes,url.getPath(),path);
					}else if("jar".equals(protocol)){
						JarURLConnection jarURLConnection = (JarURLConnection)url.openConnection();
						this.doSearchForJar(classes,jarURLConnection.getJarFile());
					}
				}
			}
		}catch(IOException e){
			e.printStackTrace();
		}
		return classes;
	}

	/**
	 * 搜索jar中的class
	 * 
	 * @param classes
	 * @param jarFile
	 */
	private void doSearchForJar(Set<Class<?>> classes,JarFile jarFile){
		if(null == jarFile){
			return;
		}
		Enumeration<JarEntry> entries = jarFile.entries();
		if(null == entries){
			return;
		}
		while(entries.hasMoreElements()){
			JarEntry jarEntry = entries.nextElement();
			if(null == jarEntry || jarEntry.isDirectory()){// 如果是目录,则扫描下一个jarEntry
				continue;
			}
			String jarEntryName = jarEntry.getName();
			if(!jarEntryName.endsWith(".class")){// 如果后缀不为.class,则扫描下一个jarEntry
				continue;
			}
			String className = jarEntryName.substring(0,jarEntryName.length() - 6);
			if(!this.doMatch(className)){
				continue;
			}
			Class<?> classInfo = ClassUtil.forName(className.replace("/","."));// 将斜线替换为报名
			if(null == classInfo){
				continue;
			}
			if(!this.doFilter(classInfo)){
				continue;
			}
			classes.add(classInfo);
		}
	}

	private void doSearchForDir(Set<Class<?>> classes,String path,String root){
		File file = new File(path);
		// 如果文件等于空,或不存在或不为目录,则返回
		if(null == file || !file.exists() || !file.isDirectory()){
			return;
		}
		File files[] = file.listFiles(this);
		if(null == files || files.length == 0){
			return;
		}
		for(int i = 0;i < files.length;i++){
			File childFile = files[i];
			if(!childFile.exists()){
				continue;
			}
			if(childFile.isDirectory()){// 如果扫描到的文件类型为目录,则继续调用次方法扫描
				this.doSearchForDir(classes,childFile.getPath(),root+"/"+childFile.getName());// 递归遍历的过程
			}else{
				String filePath = childFile.getPath();
				if(filePath.indexOf("\\") != -1){
					filePath = filePath.replace("\\","/");
				}
				String className = null;
				if(StringUtil.isBlank(root)){
					className = childFile.getName();
				}else{
					className = root + "/" + filePath.substring(path.length() + 1);
				}
				className = className.substring(0,className.length() - 6);
				if(className.startsWith("/")){
					className = className.substring(1);
				}
				if(!this.doMatch(className)){
					continue;
				}
				Class<?> classInfo = ClassUtil.forName(className.replace("/","."));// 将斜线替换为报名
				if(null == classInfo){
					continue;
				}
				if(!this.doFilter(classInfo)){
					continue;
				}
				classes.add(classInfo);
			}
		}
	}

	// 此处实现File的过滤器,过滤出class文件
	@Override
	public boolean accept(File pathName){
		return pathName.isDirectory() || pathName.getName().endsWith(".class");
	}

}