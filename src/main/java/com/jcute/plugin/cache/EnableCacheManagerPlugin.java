package com.jcute.plugin.cache;

import java.lang.annotation.Annotation;
import java.util.Map;

import com.jcute.core.bean.BeanFactory;
import com.jcute.core.bean.BeanScope;
import com.jcute.core.bean.imp.BeanDefinitionByInstance;
import com.jcute.core.context.ApplicationContext;
import com.jcute.core.error.BeanDefinitionMultipleException;
import com.jcute.core.error.ContextInitialException;
import com.jcute.core.plugin.Plugin;
import com.jcute.core.scan.PackageScanner;
import com.jcute.core.util.StringUtil;
import com.jcute.plugin.cache.imp.DefaultCacheManager;

public class EnableCacheManagerPlugin extends Plugin{

	public EnableCacheManagerPlugin(ApplicationContext applicationContext,Annotation annotation){
		super(applicationContext,annotation);
	}

	// 扫描前,添加扫描注解实现功能组件
	@Override
	public void onBeforeScanning(PackageScanner packageScanner) throws ContextInitialException{
		packageScanner.addPattern(Cache.class.getPackage().getName());
	}

	@Override
	public void onBeforeInitial() throws ContextInitialException{
		EnableCacheManager enableCacheManager = this.getAnnotation();
		String cacheManagerName = enableCacheManager.cacheManager();
		ApplicationContext applicationContext = this.getApplicationContext();
		BeanFactory beanFactory = this.getBeanFactory();
		// 读取已配置的CacheManager对象
		Map<String,CacheManager> mapping = applicationContext.getBeans(CacheManager.class);
		if(StringUtil.isBlank(cacheManagerName)){// 如果未显示指定cacheManager的名称
			if(mapping.size() == 0){// 且cacheManager对象为0个时,启用DefaultCacheManager
				CacheManager defaultCacheManager = new DefaultCacheManager();
				try{
					beanFactory.addBeanDefinition(new BeanDefinitionByInstance(beanFactory,CacheManager.class,DefaultCacheManager.DEFAULT_CACHEMANAGER_NAME,BeanScope.Singleton,defaultCacheManager));
				}catch(BeanDefinitionMultipleException e){
					throw new ContextInitialException(e.getMessage(),e);
				}
			}else if(mapping.size() > 1){// 如果CacheManager为1个时默认使用,大于1个时需要显示在EnableCacheManager注解上指定名称
				throw new ContextInitialException("Missing cacheManager name on @EnableCacheManager");
			}
		}else{
			if(!mapping.containsKey(cacheManagerName)){// 如果指定了名称但找不到次对象,则提示错误
				throw new ContextInitialException(String.format("CacheManager not found [%s]",cacheManagerName));
			}
		}
	}

}