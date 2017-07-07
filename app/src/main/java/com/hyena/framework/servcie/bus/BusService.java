/**
 * Copyright (C) 2014 The plugin_music Project
 */
package com.hyena.framework.servcie.bus;

import android.os.Messenger;

import com.hyena.framework.servcie.BaseService;

/**
 * @author yangzc
 * @version 1.0
 *
 */
public interface BusService extends BaseService {

	String BUS_SERVICE_NAME = "service_bus";
	
	/**
	 * 初始化服务
	 */
	void initService();
	
	/*
	 * 获得远程消息
	 */
	Messenger getRemoteMessenger();
	
	/*
	 * 添加服务状态监听器
	 */
	void addBusServiceAction(IBusServiceStatusListener listener);
	
	/*
	 * 删除服务状态监听器
	 */
	void removeBusServiceAction(IBusServiceStatusListener listener);
	
}
