package com.jcute.core.bean;

import com.jcute.core.annotation.Autowired;
import com.jcute.core.annotation.Component;

@Component
public class SingletonBeanInfoEntity{
	
	@Autowired
	private PrototypeBeanInfoEntity prototypeBeanInfoEntity;
	
	public String getName(){
		System.out.println(this.prototypeBeanInfoEntity);
		return "SingletonBeanInfoEntity";
	}

}