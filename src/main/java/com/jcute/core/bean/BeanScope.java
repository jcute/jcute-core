package com.jcute.core.bean;

public enum BeanScope{

	Singleton, // 单例模式,指定对象在整个生命周期中只有一个实例对象
	Prototype// 多例模式,指定对象在整个生命周期中根据调用或引用的次数创建

}