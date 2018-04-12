package com.jcute.core.bean.abs;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import com.jcute.core.bean.BeanDefinition;
import com.jcute.core.bean.BeanFactory;
import com.jcute.core.bean.BeanFactoryManager;
import com.jcute.core.bean.BeanFactoryProcessor;
import com.jcute.core.bean.BeanScope;
import com.jcute.core.bean.imp.BeanDefinitionByClass;
import com.jcute.core.bean.imp.BeanDefinitionByMethod;
import com.jcute.core.error.BeanDefinitionMultipleException;
import com.jcute.core.error.BeanDefinitionNotFoundException;
import com.jcute.core.error.BeanInstanceNotFoundException;
import com.jcute.core.logging.Logger;
import com.jcute.core.logging.LoggerFactory;
import com.jcute.core.util.ReflectionUtil;
import com.jcute.core.util.StringUtil;

public abstract class AbstractBeanFactoryManager implements BeanFactoryManager{
	
	private static final Logger logger = LoggerFactory.getLogger(AbstractBeanFactoryManager.class);
	
	private final Map<String,BeanDefinition> beanDefinitions = new ConcurrentHashMap<String,BeanDefinition>();

	@Override
	public boolean containsBean(Class<?> beanType){
		if(null == beanType){
			return false;
		}
		try{
			Map<String,BeanDefinition> mapping = this.getBeanDefinitions(beanType);
			if(null == mapping || mapping.size() == 0){
				return false;
			}
			return true;
		}catch(BeanDefinitionNotFoundException e){
			return false;
		}
	}

	@Override
	public boolean containsBean(String beanName){
		if(StringUtil.isBlank(beanName)){
			return false;
		}
		try{
			this.getBeanDefinition(beanName);
			return true;
		}catch(BeanDefinitionNotFoundException e){
			return false;
		}catch(BeanDefinitionMultipleException e){
			return true;
		}
	}

	@Override
	public boolean containsBean(Class<?> beanType,String beanName){
		try{
			this.getBean(beanType,beanName);
			return true;
		}catch(BeanDefinitionNotFoundException e){
			return false;
		}catch(BeanDefinitionMultipleException e){
			return true;
		}
	}

	@Override
	public Object getBean(Class<?> beanType) throws BeanDefinitionNotFoundException,BeanDefinitionMultipleException{
		try{
			return this.getBeanDefinition(beanType).getBeanInstance();
		}catch(BeanInstanceNotFoundException e){
			throw new BeanDefinitionNotFoundException(String.format("Bean instance not found : %s",beanType.getName()));
		}
	}

	@Override
	public Object getBean(String beanName) throws BeanDefinitionNotFoundException,BeanDefinitionMultipleException{
		try{
			return this.getBeanDefinition(beanName).getBeanInstance();
		}catch(BeanInstanceNotFoundException e){
			throw new BeanDefinitionNotFoundException(String.format("Bean instance not found : %s",beanName));
		}
	}

	@Override
	public Object getBean(Class<?> beanType,String beanName) throws BeanDefinitionNotFoundException,BeanDefinitionMultipleException{
		try{
			return this.getBeanDefinition(beanType,beanName).getBeanInstance();
		}catch(BeanInstanceNotFoundException e){
			throw new BeanDefinitionNotFoundException(String.format("Bean instance not found : %s",beanType));
		}
	}

	@Override
	public BeanDefinition getBeanDefinition(Class<?> beanType) throws BeanDefinitionNotFoundException,BeanDefinitionMultipleException{
		return this.getBeanDefinition(beanType,null);
	}

	@Override
	public BeanDefinition getBeanDefinition(String beanName) throws BeanDefinitionNotFoundException,BeanDefinitionMultipleException{
		return this.getBeanDefinition(null,beanName);
	}

	@Override
	public BeanDefinition getBeanDefinition(Class<?> beanType,String beanName) throws BeanDefinitionNotFoundException,BeanDefinitionMultipleException{
		Map<String,BeanDefinition> mapping = this.getBeanDefinitions(beanType,beanName);
		if(mapping.size() == 0){
			throw new BeanDefinitionNotFoundException();
		}
		if(mapping.size() != 1){
			throw new BeanDefinitionMultipleException();
		}
		return mapping.values().iterator().next();
	}

	@Override
	public Map<String,Object> getBeans(Class<?> beanType) throws BeanDefinitionNotFoundException{
		Map<String,BeanDefinition> mapping = this.getBeanDefinitions(beanType,null);
		Map<String,Object> result = new HashMap<String,Object>();
		if(mapping.size() == 0){
			throw new BeanDefinitionNotFoundException(beanType.getName());
		}
		try{
			for(Entry<String,BeanDefinition> entry : mapping.entrySet()){
				result.put(entry.getKey(),entry.getValue().getBeanInstance());
			}
		}catch(BeanInstanceNotFoundException e){
			throw new BeanDefinitionNotFoundException(e.getMessage(),e);
		}
		return result;
	}

	@Override
	public Map<String,BeanDefinition> getBeanDefinitions(Class<?> beanType) throws BeanDefinitionNotFoundException{
		Map<String,BeanDefinition> mapping = this.getBeanDefinitions(beanType,null);
		if(mapping.size() == 0){
			throw new BeanDefinitionNotFoundException(beanType.getName());
		}
		return mapping;
	}

	@Override
	public Map<String,BeanDefinition> getAllBeanDefinitions(){
		return Collections.unmodifiableMap(this.beanDefinitions);
	}

	@Override
	public void addBeanDefinition(BeanDefinition beanDefinition) throws BeanDefinitionMultipleException{
		if(null == beanDefinition){
			throw new IllegalArgumentException("Bean definition must not be null");
		}
		if(this.containsBean(beanDefinition.getBeanType(),beanDefinition.getBeanName())){
			throw new BeanDefinitionMultipleException(beanDefinition.toString());
		}
		this.beanDefinitions.put(beanDefinition.getBeanName(),beanDefinition);
		logger.debug("Add bean definition success : {}",beanDefinition.toString());
	}
	
	@Override
	public void addBeanDefinition(Class<?> target) throws BeanDefinitionMultipleException{
		BeanFactory beanFactory = this.getBeanFactory();
		BeanFactoryProcessor beanFactoryProcessor = beanFactory.getBeanFactoryProcessor();
		BeanDefinition beanDefinition = beanFactoryProcessor.createBeanDefinition(target);
		this.addBeanDefinition(beanDefinition);
		
		if(!(beanDefinition instanceof BeanDefinitionByClass)){
			return;
		}
		for(Method method : ReflectionUtil.getDeclaredMethods(target)){
			if(!beanFactoryProcessor.isComponent(method)){
				continue;
			}
			String childBeanName = beanFactoryProcessor.resolveBeanName(method);
			BeanScope childBeanScope = beanFactoryProcessor.resolveBeanScope(method);
			BeanDefinition methodBeanDefinition = new BeanDefinitionByMethod(beanFactory,method.getReturnType(),childBeanName,childBeanScope,beanDefinition,method);
			this.addBeanDefinition(methodBeanDefinition);
		}
	}

	@Override
	public void addMethodBeanDefinition(Class<?> target) throws BeanDefinitionMultipleException{
		
	}

	protected abstract BeanFactory getBeanFactory();
	
	protected void clearBeanDefinitions() {
		this.beanDefinitions.clear();
	}
	
	private Map<String,BeanDefinition> getBeanDefinitions(Class<?> beanType,String beanName){
		Map<String,BeanDefinition> result = new HashMap<String,BeanDefinition>();
		if(null == beanType && StringUtil.isBlank(beanName)){// 两个参数都为空,返回空
			return result;
		}
		if(null == beanType && StringUtil.isNotBlank(beanName)){// 类型为空且名称不为空
			if(this.beanDefinitions.containsKey(beanName)){
				result.put(beanName,this.beanDefinitions.get(beanName));
			}
			return result;
		}
		if(null != beanType && StringUtil.isNotBlank(beanName)){
			for(Entry<String,BeanDefinition> entry : this.beanDefinitions.entrySet()){
				if(entry.getValue().isAssignable(beanType) && entry.getKey().equals(beanName)){
					result.put(entry.getKey(),entry.getValue());
					return result;
				}
			}
		}
		for(Entry<String,BeanDefinition> entry : this.beanDefinitions.entrySet()){
			if(entry.getValue().isAssignable(beanType)){
				result.put(entry.getKey(),entry.getValue());
			}
		}
		return result;
	}

}