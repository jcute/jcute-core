package com.jcute.core.bean.abs;

import com.jcute.core.bean.BeanDefinition;
import com.jcute.core.bean.BeanFactory;
import com.jcute.core.bean.BeanScope;
import com.jcute.core.error.BeanInstanceNotFoundException;
import com.jcute.core.error.ContextCreateException;
import com.jcute.core.error.ContextDestoryException;
import com.jcute.core.error.ContextInitialException;
import com.jcute.core.error.ContextInjectException;
import com.jcute.core.util.StringUtil;

public abstract class AbstractBeanDefinition implements BeanDefinition{

	private final BeanFactory beanFactory;
	private final Class<?> beanType;
	private final String beanName;
	private final BeanScope beanScope;

	public AbstractBeanDefinition(BeanFactory beanFactory,Class<?> beanType,String beanName,BeanScope beanScope){
		if(null == beanFactory){
			throw new IllegalArgumentException("Bean factory must not be null");
		}
		if(null == beanType){
			throw new IllegalArgumentException("Bean type must not be null");
		}
		this.beanFactory = beanFactory;
		this.beanType = beanType;
		this.beanName = this.formatBeanName(beanType,beanName);
		this.beanScope = null == beanScope ? BeanScope.Singleton : beanScope;
	}

	@Override
	public BeanFactory getBeanFactory(){
		return this.beanFactory;
	}

	@Override
	public Class<?> getBeanType(){
		return this.beanType;
	}

	@Override
	public String getBeanName(){
		return this.beanName;
	}

	@Override
	public BeanScope getBeanScope(){
		return this.beanScope;
	}

	@Override
	public boolean isSingleton(){
		return this.beanScope == BeanScope.Singleton;
	}

	@Override
	public boolean isPrototype(){
		return this.beanScope == BeanScope.Prototype;
	}

	@Override
	public boolean isAssignable(Class<?> target){
		if(null == target){
			return false;
		}
		return target.isAssignableFrom(this.beanType);
	}

	@Override
	public Object getBeanInstance() throws BeanInstanceNotFoundException{
		Object beanInstance = this.doGetBeanInstance();
		if(null == beanInstance){
			throw new BeanInstanceNotFoundException(this.toString());
		}
		return beanInstance;
	}

	@Override
	public void initial() throws ContextInitialException{
		this.getBeanDefinitionHandler().initial();
	}

	@Override
	public void destory() throws ContextDestoryException{
		this.getBeanDefinitionHandler().destory();
	}

	@Override
	public void create() throws ContextCreateException{
		this.getBeanDefinitionHandler().create();
	}

	@Override
	public void inject() throws ContextInjectException{
		this.getBeanDefinitionHandler().inject();
	}

	@Override
	public int hashCode(){
		return this.toString().hashCode();
	}

	@Override
	public boolean equals(Object obj){
		if(null == obj){
			return false;
		}
		if(obj instanceof BeanDefinition){
			return this.toString().equals(obj.toString());
		}
		return false;
	}

	@Override
	public String toString(){
		return String.format("[%s]%s#%s",this.beanScope,this.beanType.getName(),this.beanName);
	}

	private String formatBeanName(Class<?> beanType,String beanName){
		if(StringUtil.isNotBlank(beanName)){
			return beanName;
		}
		String newBeanName = beanType.getSimpleName();
		return newBeanName.substring(0,1).toLowerCase() + newBeanName.substring(1);
	}

	protected Object doGetBeanInstance() throws BeanInstanceNotFoundException{
		return this.getBeanDefinitionHandler().getBeanInstance();
	}

}