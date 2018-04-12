package com.jcute.core.bean.abs;

import com.jcute.core.bean.BeanDefinition;
import com.jcute.core.bean.BeanDefinitionCreator;
import com.jcute.core.error.BeanInstanceCreateException;

/**
 * Bean创建器的抽象类,强制要求构造函数必须传入BeanDefinition对象<br/>
 * 将createBeanInstance方法进一步包装,方便特殊需求扩展
 * 
 * @author koko
 *
 */
public abstract class AbstractBeanDefinitionCreator implements BeanDefinitionCreator{

	private final BeanDefinition beanDefinition;

	public AbstractBeanDefinitionCreator(BeanDefinition beanDefinition){
		if(null == beanDefinition){
			throw new IllegalArgumentException("Bean definition must not be null");
		}
		this.beanDefinition = beanDefinition;
	}

	@Override
	public Object createBeanInstace() throws BeanInstanceCreateException{
		Object beanInstance = this.doCreateBeanInstance();
		if(null == beanInstance){// 此处判断是否为空,避免因用户实现导致返回Null的情况
			throw new BeanInstanceCreateException("Create bean instance failed");
		}
		return beanInstance;
	}

	@Override
	public BeanDefinition getBeanDefinition(){
		return this.beanDefinition;
	}

	protected abstract Object doCreateBeanInstance() throws BeanInstanceCreateException;

}