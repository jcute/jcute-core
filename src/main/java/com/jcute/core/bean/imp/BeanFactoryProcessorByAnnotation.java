package com.jcute.core.bean.imp;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.jcute.core.annotation.Autowired;
import com.jcute.core.annotation.Component;
import com.jcute.core.annotation.ComponentScan;
import com.jcute.core.annotation.Configuration;
import com.jcute.core.annotation.Destory;
import com.jcute.core.annotation.ImportConfig;
import com.jcute.core.annotation.Initial;
import com.jcute.core.annotation.Interceptor;
import com.jcute.core.annotation.Order;
import com.jcute.core.annotation.Property;
import com.jcute.core.bean.BeanDefinition;
import com.jcute.core.bean.BeanFactory;
import com.jcute.core.bean.BeanScope;
import com.jcute.core.bean.abs.AbstractBeanFactoryProcessor;
import com.jcute.core.config.ConfigManager;
import com.jcute.core.context.ApplicationContext;
import com.jcute.core.error.BeanDefinitionMultipleException;
import com.jcute.core.error.BeanDefinitionNotFoundException;
import com.jcute.core.logging.Logger;
import com.jcute.core.logging.LoggerFactory;
import com.jcute.core.match.PathMatcher;
import com.jcute.core.match.imp.DefaultPathMatcher;
import com.jcute.core.plugin.Plugin;
import com.jcute.core.plugin.PluginManager;
import com.jcute.core.proxy.Proxy;
import com.jcute.core.resource.Resource;
import com.jcute.core.scan.PackageScanner;
import com.jcute.core.scan.PackageScannerFilter;
import com.jcute.core.scan.imp.DefaultPackageScanner;
import com.jcute.core.toolkit.DirectedGraph;
import com.jcute.core.util.AnnotationUtil;
import com.jcute.core.util.ReflectionUtil;
import com.jcute.core.util.ResourceUtil;
import com.jcute.core.util.StringUtil;

public class BeanFactoryProcessorByAnnotation extends AbstractBeanFactoryProcessor implements Comparator<Method>, PackageScannerFilter{

	private static final Logger logger = LoggerFactory.getLogger(BeanFactoryProcessorByAnnotation.class);

	private Map<String,Set<String>> cachePatterns = new HashMap<String,Set<String>>();
	private PathMatcher pathMatcher = new DefaultPathMatcher(".");
	private ApplicationContext applicationContext;

	public BeanFactoryProcessorByAnnotation(BeanFactory beanFactory){
		super(beanFactory);
		this.applicationContext = beanFactory.getApplicationContext();
	}

	/**
	 * 发现参数含有@Autowired的参数则从BeanFactory寻找对象
	 */
	@Override
	protected Object[] getArguments(Class<?>[] parameterTypes,Annotation[][] annotations) throws BeanDefinitionNotFoundException,BeanDefinitionMultipleException{
		if(null == parameterTypes || parameterTypes.length == 0){
			return new Object[0];
		}
		BeanFactory beanFactory = this.getBeanFactory();
		ConfigManager configManager = beanFactory.getConfigManager();
		Annotation[] autowiredAnnotations = AnnotationUtil.getAnnotation(parameterTypes,annotations,Autowired.class);
		Annotation[] propertyAnnotations = AnnotationUtil.getAnnotation(parameterTypes,annotations,Property.class);
		Object[] result = new Object[parameterTypes.length];
		for(int i = 0;i < parameterTypes.length;i++){
			Class<?> parameterType = parameterTypes[i];
			Annotation autowiredAnnotation = autowiredAnnotations[i];
			Annotation propertyAnnotation = propertyAnnotations[i];
			if(null != autowiredAnnotation){
				Autowired autowired = (Autowired)autowiredAnnotation;
				String beanName = autowired.value();
				Class<?> beanType = parameterTypes[i];
				result[i] = beanFactory.getBean(beanType,beanName);
			}else if(null != propertyAnnotation){
				Property property = (Property)propertyAnnotation;
				result[i] = configManager.getConfigValue(property.value(),parameterType);
			}else{
				result[i] = null;
			}
		}
		return result;
	}

	/**
	 * 选举构造函数
	 * <ul>
	 * <li>1.寻找@Initial注解的构造函数</li>
	 * <li>2.寻找参数包含@Autowired注解的构造函数</li>
	 * <li>3.使用构造函数列表中的第0个位置</li>
	 * </ul>
	 */
	@Override
	protected Constructor<?> getConstructor(Constructor<?>[] constructors){
		Constructor<?> constructor = null;
		for(int i = 0;i < constructors.length;i++){
			Constructor<?> tempConstructor = constructors[i];
			if(AnnotationUtil.hasAnnotation(tempConstructor,Initial.class)){
				constructor = tempConstructor;
				break;
			}
		}
		if(null == constructor){
			for(int i = 0;i < constructors.length;i++){
				Constructor<?> tempConstructor = constructors[i];
				if(AnnotationUtil.hasAnnotation(tempConstructor.getParameterAnnotations(),Autowired.class)){
					constructor = tempConstructor;
					break;
				}
			}
		}
		if(null == constructor){
			constructor = constructors[0];
		}
		return constructor;
	}

	@Override
	protected boolean isComponentClass(Class<?> beanType){
		return AnnotationUtil.hasAnnotation(beanType,Component.class);
	}

	@Override
	public boolean isComponent(Method method){
		if(null == method){
			return false;
		}
		if(!AnnotationUtil.hasAnnotation(method,Component.class)){
			return false;
		}
		if(method.getReturnType().equals(Void.TYPE)){
			return false;
		}
		if(method.getReturnType().isPrimitive()){
			return false;
		}
		return true;
	}

	@Override
	public boolean isConfiguration(Class<?> beanType){
		return AnnotationUtil.hasAnnotation(beanType,Configuration.class);
	}

	@Override
	public void injectBeanInstance(Object beanInstance){
		BeanFactory beanFactory = this.getBeanFactory();
		ConfigManager configManager = beanFactory.getConfigManager();

		Class<?> beanInstanceType = beanInstance.getClass();
		Field[] fields = ReflectionUtil.getDeclaredFields(beanInstanceType);
		if(null != fields && fields.length > 0){
			for(int i = 0;i < fields.length;i++){
				Field field = fields[i];
				if(AnnotationUtil.hasAnnotation(field,Autowired.class) && !field.getType().isPrimitive()){
					Autowired autowired = AnnotationUtil.getAnnotation(field,Autowired.class);
					Class<?> beanType = field.getType();
					String beanName = autowired.value();
					try{
						ReflectionUtil.makeAccessible(field);
						field.set(beanInstance,beanFactory.getBean(beanType,beanName));
					}catch(Exception e){
						e.printStackTrace();
					}
				}
				if(AnnotationUtil.hasAnnotation(field,Property.class)){
					Property property = AnnotationUtil.getAnnotation(field,Property.class);
					if(StringUtil.isNotBlank(property.value())){
						try{
							Object fieldValue = configManager.getConfigValue(property.value(),field.getType());
							ReflectionUtil.makeAccessible(field);
							field.set(beanInstance,fieldValue);
						}catch(IllegalArgumentException e){
							e.printStackTrace();
						}catch(IllegalAccessException e){
							e.printStackTrace();
						}
					}
				}
			}
		}

		Method[] methods = ReflectionUtil.getDeclaredMethods(beanInstanceType);
		if(null != methods && methods.length > 0){
			for(int i = 0;i < methods.length;i++){
				Method method = methods[i];
				if(AnnotationUtil.hasAnnotation(method,Autowired.class) || AnnotationUtil.hasAnnotation(method,Property.class)){
					try{
						Object[] parameterDatas = this.getMethodArguments(method);
						ReflectionUtil.makeAccessible(method);
						ReflectionUtil.invokeMethod(method,beanInstance,parameterDatas);
					}catch(Exception e){
						e.printStackTrace();
					}
				}
			}
		}
	}

	@Override
	protected Method[] getInitialMethods(Object beanInstance){
		Class<?> beanType = beanInstance.getClass();
		List<Method> result = new ArrayList<Method>();
		Method[] methods = ReflectionUtil.getDeclaredMethods(beanType);
		if(null == methods || methods.length == 0){
			return new Method[0];
		}
		for(int i = 0;i < methods.length;i++){
			Method method = methods[i];
			if(!AnnotationUtil.hasAnnotation(method,Initial.class)){
				continue;
			}
			result.add(method);
		}
		Collections.sort(result,this);
		return result.toArray(new Method[result.size()]);
	}

	@Override
	protected Method[] getDestoryMethods(Object beanInstance){
		Class<?> beanType = beanInstance.getClass();
		List<Method> result = new ArrayList<Method>();
		Method[] methods = ReflectionUtil.getDeclaredMethods(beanType);
		if(null == methods || methods.length == 0){
			return new Method[0];
		}
		for(int i = 0;i < methods.length;i++){
			Method method = methods[i];
			if(!AnnotationUtil.hasAnnotation(method,Destory.class)){
				continue;
			}
			result.add(method);
		}
		Collections.sort(result,this);
		return result.toArray(new Method[result.size()]);
	}

	@Override
	public int compare(Method o1,Method o2){
		int s1 = 0;
		int s2 = 0;
		if(AnnotationUtil.hasAnnotation(o1,Order.class)){
			s1 = o1.getAnnotation(Order.class).value();
		}
		if(AnnotationUtil.hasAnnotation(o2,Order.class)){
			s2 = o2.getAnnotation(Order.class).value();
		}
		return s1 - s2;
	}

	@Override
	protected Set<String> doSearchScanPattern(String root){
		if(this.cachePatterns.containsKey(root)){
			return this.cachePatterns.get(root);
		}
		Set<String> patterns = new LinkedHashSet<String>();
		this.doSearchScanPattern(root,patterns);
		if(null != patterns && patterns.size() > 0){
			this.cachePatterns.put(root,patterns);
		}
		return patterns;
	}

	@Override
	protected BeanDefinition[] doSearchInterceptor(Class<?> beanType){
		BeanFactory beanFactory = this.getBeanFactory();
		List<BeanDefinition> result = new ArrayList<BeanDefinition>();
		Map<String,BeanDefinition> mappings = beanFactory.getAllBeanDefinitions();
		for(Entry<String,BeanDefinition> entry : mappings.entrySet()){
			BeanDefinition beanDefinition = entry.getValue();
			Class<?> interceptorBeanType = beanDefinition.getBeanType();
			if(!AnnotationUtil.hasAnnotation(interceptorBeanType,Interceptor.class)){
				continue;
			}
			if(!Proxy.class.isAssignableFrom(interceptorBeanType)){
				continue;
			}
			Interceptor interceptor = AnnotationUtil.getAnnotation(interceptorBeanType,Interceptor.class);
			if(!this.isInterceptTargetClass(interceptor,beanType,interceptorBeanType)){
				continue;
			}
			result.add(beanDefinition);
		}
		if(null != result && result.size() > 0){
			logger.debug("Found interceptor {} -> {}",beanType.getName(),result);
			
		}
		return result.toArray(new BeanDefinition[result.size()]);
	}

	@Override
	protected void processDirectedGraph(BeanDefinition beanDefinition,DirectedGraph<BeanDefinition> directedGraph) throws BeanDefinitionNotFoundException,BeanDefinitionMultipleException{
		BeanFactory beanFactory = this.getBeanFactory();
		Class<?> beanType = beanDefinition.getBeanType();
		if(AnnotationUtil.hasAnnotation(beanType,Configuration.class)){
			Configuration configuration = AnnotationUtil.getAnnotation(beanType,Configuration.class);
			ImportConfig[] configs = configuration.configs();
			if(null != configs && configs.length > 0){
				ConfigManager configManager = beanFactory.getConfigManager();
				for(int i = 0;i < configs.length;i++){
					ImportConfig config = configs[i];
					Resource resource = ResourceUtil.getResource(config.value());
					configManager.addConfig(resource,config.type());
					logger.debug("Add config [{}]{}",config.type(),config.value());
				}
			}
		}

		if(beanDefinition instanceof BeanDefinitionByMethod){
			BeanDefinitionByMethod beanDefinitionByMethod = (BeanDefinitionByMethod)beanDefinition;
			BeanDefinition parentBeanDefinition = beanDefinitionByMethod.getParentBeanDefinition();
			Method method = beanDefinitionByMethod.getBeanCreateMethod();
			directedGraph.addEdge(beanDefinition,parentBeanDefinition);// 添加父bean的依赖
			Class<?>[] parameterTypes = method.getParameterTypes();
			if(null == parameterTypes || parameterTypes.length == 0){
				directedGraph.addNode(beanDefinition);
				return;
			}
			// 添加方法注入依赖
			Annotation[] annotations = AnnotationUtil.getAnnotation(parameterTypes,method.getParameterAnnotations(),Autowired.class);
			for(int i = 0;i < parameterTypes.length;i++){
				if(null == annotations[i]){
					continue;
				}
				Class<?> parameterType = parameterTypes[i];
				Autowired autowired = (Autowired)annotations[i];
				String beanName = autowired.value();
				BeanDefinition dependencyBeanDefinition = beanFactory.getBeanDefinition(parameterType,beanName);
				directedGraph.addEdge(beanDefinition,dependencyBeanDefinition);
			}
		}else if(beanDefinition instanceof BeanDefinitionByInstance){
			directedGraph.addNode(beanDefinition);
		}else if(beanDefinition instanceof BeanDefinitionByClass){
			Constructor<?> beanConstructor = this.getBeanConstructor(beanDefinition.getBeanType());
			Class<?>[] parameterTypes = beanConstructor.getParameterTypes();
			if(null != parameterTypes && parameterTypes.length > 0) {
				// 添加构造函数依赖
				Annotation[] annotations = AnnotationUtil.getAnnotation(parameterTypes,beanConstructor.getParameterAnnotations(),Autowired.class);
				for(int i = 0;i < parameterTypes.length;i++){
					if(null == annotations[i]){
						continue;
					}
					Class<?> parameterType = parameterTypes[i];
					Autowired autowired = (Autowired)annotations[i];
					String beanName = autowired.value();
					BeanDefinition dependencyBeanDefinition = beanFactory.getBeanDefinition(parameterType,beanName);
					directedGraph.addEdge(beanDefinition,dependencyBeanDefinition);
				}
			}
			directedGraph.addNode(beanDefinition);
		}
	}

	@Override
	public BeanDefinition createBeanDefinition(Class<?> target){
		String beanName = null;
		BeanScope beanScope = null;
		if(AnnotationUtil.hasAnnotation(target,Component.class)){
			Component component = AnnotationUtil.getAnnotation(target,Component.class);
			beanName = component.value();
			beanScope = component.scope();
		}
		BeanFactory beanFactory = this.getBeanFactory();
		BeanDefinition beanDefinition = new BeanDefinitionByClass(beanFactory,target,beanName,beanScope);
		return beanDefinition;
	}

	@Override
	public boolean doFilter(Class<?> target){
		return AnnotationUtil.hasAnnotation(target,Configuration.class) || AnnotationUtil.hasAnnotation(target,Component.class);
	}

	@Override
	public String resolveBeanName(Method method){
		String beanName = method.getName();
		if(AnnotationUtil.hasAnnotation(method,Component.class)){
			Component component = AnnotationUtil.getAnnotation(method,Component.class);
			if(StringUtil.isNotBlank(component.value())){
				beanName = component.value();
			}
		}
		return beanName;
	}

	@Override
	public BeanScope resolveBeanScope(Method method){
		BeanScope beanScope = BeanScope.Singleton;
		if(AnnotationUtil.hasAnnotation(method,Component.class)){
			Component component = AnnotationUtil.getAnnotation(method,Component.class);
			if(StringUtil.isNotBlank(component.value())){
				beanScope = component.scope();
			}
		}
		return beanScope;
	}

	/**
	 * 判断指定的拦截器注解,是否拦截指定的class
	 * 
	 * @param interceptor
	 * @param beanType
	 * @return true为拦截
	 */
	private boolean isInterceptTargetClass(Interceptor interceptor,Class<?> beanType,Class<?> interceptorType){
		if(beanType.equals(interceptorType)){// 剔除掉当前类,代理当前类的情况
			return false;
		}
		String[] packages = interceptor.packages();
		Class<? extends Annotation>[] annotations = interceptor.annotations();
		String className = beanType.getName();
		if(null != packages && packages.length > 0){
			for(int i = 0;i < packages.length;i++){
				String packagePattern = packages[i];
				if(this.pathMatcher.isPattern(packagePattern)){
					if(this.pathMatcher.match(packagePattern,className)){
						return true;
					}
				}else{
					if(packagePattern.equals(className)){
						return true;
					}
				}
			}
		}
		if(null != annotations && annotations.length > 0){
			for(int i = 0;i < annotations.length;i++){
				Class<? extends Annotation> annotation = annotations[i];
				if(AnnotationUtil.hasAnnotation(beanType,annotation)){
					return true;
				}
			}
		}
		return false;
	}

	private void doSearchScanPattern(String root,Set<String> patterns){
		PackageScanner packageScanner = new DefaultPackageScanner();
		packageScanner.addPattern(root);
		packageScanner.addPackageScannerFilter(this);
		for(Class<?> clazz : packageScanner.scan()){
			if(AnnotationUtil.hasAnnotation(clazz,ComponentScan.class)){
				ComponentScan componentScan = AnnotationUtil.getAnnotation(clazz,ComponentScan.class);
				String[] tempPatterns = componentScan.value();
				if(null == tempPatterns || tempPatterns.length == 0){
					continue;
				}
				for(int i = 0;i < tempPatterns.length;i++){
					String tempPattern = tempPatterns[i];
					if(StringUtil.isBlank(tempPattern)){
						continue;
					}
					if(patterns.contains(tempPattern)){
						continue;
					}
					patterns.add(tempPattern);
					this.doSearchScanPattern(tempPattern,patterns);
				}
			}
			this.initPlugin(clazz);
		}
	}

	private void initPlugin(Class<?> clazz){
		PluginManager pluginManager = this.getBeanFactory().getPluginManager();
		Map<Annotation,Class<? extends Plugin>> mapping = pluginManager.searchPlugins(clazz);
		if(null == mapping || mapping.size() == 0){
			return;
		}
		for(Entry<Annotation,Class<? extends Plugin>> entry : mapping.entrySet()){
			try{
				Class<? extends Plugin> plugin = entry.getValue();
				Constructor<?> constructor = plugin.getConstructor(new Class<?>[]{ApplicationContext.class,Annotation.class});
				Object instance = constructor.newInstance(new Object[]{this.applicationContext,entry.getKey()});
				pluginManager.addPlugin((Plugin)instance);
				logger.debug("Create plugin success @{} -> {}",entry.getKey().annotationType().getName(),entry.getValue().getName());
			}catch(Throwable t){
				logger.warn("Create plugin failed @{} -> {}",entry.getKey().annotationType().getName(),entry.getValue().getName());
			}
		}
	}

}