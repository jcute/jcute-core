package com.jcute.core.scan.abs;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

import com.jcute.core.match.PathMatcher;
import com.jcute.core.match.imp.DefaultPathMatcher;
import com.jcute.core.scan.PackageScanner;
import com.jcute.core.scan.PackageScannerFilter;
import com.jcute.core.util.StringUtil;

/**
 * 包扫描器的抽象方法<br/>
 * 实现基本的addPattern和addFilter功能
 */
public abstract class AbstractPackageScanner implements PackageScanner{

	private final Set<String> patterns;
	private final PathMatcher pathMatcher;
	private final Set<PackageScannerFilter> filters;

	public AbstractPackageScanner(){
		this.patterns = new LinkedHashSet<String>();
		this.pathMatcher = this.createPathMatcher();
		this.filters = new LinkedHashSet<PackageScannerFilter>();
	}

	@Override
	public void addPattern(String pattern){
		if(StringUtil.isBlank(pattern)){
			throw new IllegalArgumentException("Pattern must not be null");
		}
		if(this.pathMatcher.isPattern(pattern)){
			this.patterns.add(pattern);
		}else{
			if(pattern.endsWith(".")){
				this.patterns.add(String.format("%s**",pattern));
			}else{
				this.patterns.add(String.format("%s.**",pattern));
			}
		}
	}

	@Override
	public void addPattern(Set<String> patterns){
		if(null == patterns || patterns.size() == 0){
			return;
		}
		for(String pattern : patterns){
			this.addPattern(pattern);
		}
	}

	@Override
	public void addPackageScannerFilter(PackageScannerFilter packageScannerFilter){
		if(null == packageScannerFilter){
			throw new IllegalArgumentException("Package scanner filter must not be null");
		}
		this.filters.add(packageScannerFilter);
	}

	@Override
	public Set<Class<?>> scan(){
		return this.doScan();
	}

	/**
	 * 对扫描到的类路径进行匹配<br/>
	 * 
	 * @param path 格式为 com/demo/DemoInfo.class
	 * @return true:通过,false:不通过
	 */
	protected boolean doMatch(String path){
		if(StringUtil.isBlank(path)){// path为空不通过
			return false;
		}
		if(this.patterns.isEmpty()){// 如果通配符为空,返回true
			return true;
		}
		String newPath = path;
		if(newPath.startsWith("/") || newPath.startsWith("\\")){// 截取开头为斜线
			newPath = newPath.substring(1);
		}
		if(newPath.endsWith(".class")){// 截去.class后缀
			newPath = newPath.substring(0,newPath.length() - 6);
		}
		if(newPath.indexOf("/") > 0){// 处理包斜线
			newPath = newPath.replace("/",".");
		}
		if(newPath.indexOf("\\") > 0){// 处理路径斜线
			newPath = newPath.replace("\\",".");
		}
		// 此处应得到path结果为 com.demo.DemoInfo
		for(String pattern : this.patterns){
			if(this.pathMatcher.match(pattern,newPath)){
				return true;
			}
		}
		return false;
	}

	/**
	 * 执行class过滤操作
	 * 
	 * @param target
	 * @return true:通过,false:不通过
	 */
	protected boolean doFilter(Class<?> target){
		if(null == target){// 如果class为空,不通过
			return false;
		}
		if(this.filters.isEmpty()){// 过滤器为空,说明不拦截,通过
			return true;
		}
		for(PackageScannerFilter filter : this.filters){
			if(filter.doFilter(target)){// 多每个过滤器进行调用,只要有过滤器通过的则通过,一票通过策略
				return true;
			}
		}
		return false;
	}

	/**
	 * 返回可扫描包路径信息
	 * 
	 * @return 返回可扫描包列表
	 */
	protected Set<String> getScanPaths(){
		Set<String> paths = new LinkedHashSet<String>();
		for(String pattern : this.patterns){
			String newPattern = pattern;
			if(newPattern.indexOf("*") != -1){// 如果扫描的包通配符中存在 .*,则截取到.*之前内容保留
				newPattern = newPattern.substring(0,newPattern.indexOf("*"));
			}
			if(newPattern.indexOf("?") != -1){// 如果扫描的包通配符中存在?,则截取?之前的内容
				newPattern = newPattern.substring(0,newPattern.indexOf("?"));
				if(!newPattern.endsWith(".")){// 如果截取到?之前的内容后,剩下内容认证不到路径,则截取到路径内容
					newPattern = newPattern.substring(0,newPattern.lastIndexOf("."));
					// com.demo.?til.*
					// 1 -> com.demo.?til
					// 2 -> com.demo
				}
			}
			if(newPattern.indexOf("{") != -1){// 如果通配符中存在正则表达式,则截取正则表达式之前内容
				newPattern = newPattern.substring(0,newPattern.indexOf("{"));
				if(!newPattern.endsWith(".")){// 如果截取到{之前的内容后,剩下内容认证不到路径,则截取到路径内容
					newPattern = newPattern.substring(0,newPattern.lastIndexOf("."));
					// com.demo.a{\W+}
					// 1 -> com.demo.a
					// 2 -> com.demo
				}
			}
			if(newPattern.indexOf(".") != -1){
				newPattern = newPattern.replace(".","/");
			}
			if(newPattern.startsWith("/")){// 去掉路径开头/
				newPattern = newPattern.substring(1);
			}
			if(newPattern.endsWith("/")){// 去掉路径结尾/
				newPattern = newPattern.substring(0,newPattern.length() - 1);
			}
			paths.add(newPattern);
		}
		return Collections.unmodifiableSet(paths);
	}

	/**
	 * 返回实现类所使用的路径匹配对象,便于以后扩展
	 * 
	 * @return 默认返回AntPathMatcher对象
	 */
	protected PathMatcher createPathMatcher(){
		return new DefaultPathMatcher(".");
	}

	/**
	 * 提供给子类使用
	 * 
	 * @return 返回PathMatcher对象
	 */
	protected PathMatcher getPathMatcher(){
		return this.pathMatcher;
	}

	/**
	 * 此处做一层包装,避免以后需要公共处理扩展等
	 * 
	 * @return 返回扫描结果
	 */
	protected abstract Set<Class<?>> doScan();

}