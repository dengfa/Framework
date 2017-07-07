/*
 * Copyright (c) 2013 Baidu Inc.
 */
package com.hyena.framework.network;

import android.content.Context;
import android.net.ConnectivityManager;

import com.hyena.framework.bean.KeyValuePair;

import java.util.List;

/**
 * 网络感应器
 * @author yangzc
 * 
 * 后续可考虑把网路相应的判断逻辑加到这里来
 */
public interface NetworkSensor {

	/*
	 * 重新构建URl
	 */
	String rebuildUrl(String url);

	/*
	 * 获取通用头信息
	 */
	List<KeyValuePair> getCommonHeaders(String url, boolean isProxy);
	
	/*
	 * 代理服务器地址
	 */
	HttpExecutor.ProxyHost getProxyHost(String url, boolean isProxy);
	
	/*
	 * 发生流量
	 */
	void updateFlowRate(long len);

	/*
	 * 是否有可用网络
	 */
	boolean isNetworkAvailable();
	
	/*
	 * 获得链接管理器
	 */
	ConnectivityManager getConnectivityManager(Context context);
}
