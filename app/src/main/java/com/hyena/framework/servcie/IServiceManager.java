/*
 * Copyright (c) 2013 Baidu Inc.
 */
package com.hyena.framework.servcie;

/**
 * 服务管理器
 * @author yangzc
 *
 */
public interface IServiceManager {

	/*
	 * 获得服务
	 */
	Object getService(String name);
	
	/**
	 * 释放所有服务
	 */
	void releaseAll();
	
	/*
	 * 动态注册服务
	 */
	void registService(String name, Object service);
	
	/*
	 * 解注册服务
	 */
	void unRegistService(String name);
}
