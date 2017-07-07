package com.hyena.framework.utils;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;

import com.hyena.framework.clientlog.LogUtil;

/**
 * 消息中心
 * @author yangzc
 */
public class MsgCenter {

	private static final String  TAG   = "MsgCenter";
	private static final boolean DEBUG = false;
	
	/*
	 * 发送全局广播
	 */
	public static void sendGlobalBroadcast(Intent intent) {
		sendGlobalBroadcast(intent, null);
	}

	/*
	 * 发送全局广播
     */
	public static void sendGlobalBroadcast(Intent intent, String permission) {
		try {
			if(intent == null)
				return;

			if(DEBUG)
				LogUtil.v(TAG, "sendGlobalBroadcast, action: " + intent.getAction());
			if (TextUtils.isEmpty(permission)) {
				BaseApp.getAppContext().sendBroadcast(intent);
			} else {
				BaseApp.getAppContext().sendBroadcast(intent, permission);
			}
		} catch (Throwable e) {
			LogUtil.e(TAG, e);
		}
	}
	
	/*
	 * 顺序广播
	 */
	public static void sendGlobalOrderedBroadcast(Intent intent){
		try {
			if(intent == null)
				return;
			
			if(DEBUG)
				LogUtil.v(TAG, "sendGlobalBroadcast, action: " + intent.getAction());
			BaseApp.getAppContext().sendOrderedBroadcast(intent, null);
		} catch (Throwable e) {
			LogUtil.e(TAG, e);
		}
	}
	
	/*
	 * 接收全局广播
	 */
	public static void registerGlobalReceiver(BroadcastReceiver receiver, IntentFilter filter){
		try {
			BaseApp.getAppContext().registerReceiver(receiver, filter);
		} catch (Throwable e) {
			LogUtil.e(TAG, e);
		}
	}
	
	/*
	 * 解注册全局广播
	 */
	public static void unRegisterGlobalReceiver(BroadcastReceiver receiver){
		try {
			BaseApp.getAppContext().unregisterReceiver(receiver);
		} catch (Throwable e) {
			LogUtil.e(TAG, e);
		}
	}
	
	/*
	 * 发送本地广播
	 */
	public static void sendLocalBroadcast(Intent intent){
		try {
			if(DEBUG)
				LogUtil.v(TAG, "sendLocalBroadcast, action: " + intent.getAction());
			LocalBroadcastManager.getInstance(BaseApp.getAppContext()).sendBroadcast(intent);
		} catch (Throwable e) {
			LogUtil.e(TAG, e);
		}
	}
	
	/*
	 * 接收本地广播
	 */
	public static void registerLocalReceiver(BroadcastReceiver receiver, IntentFilter filter){
		try {
			LocalBroadcastManager.getInstance(BaseApp.getAppContext()).registerReceiver(receiver, filter);
		} catch (Throwable e) {
			LogUtil.e(TAG, e);
		}
	}
	
	/*
	 * 解注册本地广播
	 */
	public static void unRegisterLocalReceiver(BroadcastReceiver receiver){
		try {
			LocalBroadcastManager.getInstance(BaseApp.getAppContext()).unregisterReceiver(receiver);
		} catch (Throwable e) {
			LogUtil.e(TAG, e);
		}
	}
}
