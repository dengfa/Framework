/**
 * Copyright (C) 2014 The AndroidSupport Project
 */
package com.hyena.framework.app.fragment;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;

import com.hyena.framework.servcie.IServiceManager;
import com.hyena.framework.servcie.ServiceProvider;

/**
 * 基础Fragment
 * @author yangzc
 */
public class BaseFragment extends SafeFragment {

	/*
	 * 按键点击
	 */
	public boolean onKeyDown(int keyCode, KeyEvent event){
		return false;
	}

	/*
	 * 处理点击事件
	 */
	public final boolean handleKeyDown(int keyCode, KeyEvent event){
		try {
			return onKeyDown(keyCode, event);
		} catch (Throwable e) {
		}
		return true;
	}
	
	@Override
	public void onError(Throwable e) {
		super.onError(e);
//		final UncaughtExceptionHandler defaultHandler = Thread.getDefaultUncaughtExceptionHandler();
//		if(defaultHandler != null)
//			defaultHandler.uncaughtException(Thread.currentThread(), e);
	}

	/*
	 * 跟踪页面访问路径
	 */
	@Override
	public void onViewCreatedImpl(View view, Bundle savedInstanceState) {
		super.onViewCreatedImpl(view, savedInstanceState);
	}

	@Override
	public void onDestroyViewImpl() {
		super.onDestroyViewImpl();
	}

	/*
	 * 统计访问次数
	 */
	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		super.setUserVisibleHint(isVisibleToUser);
	}
	
	/*
	 * 当前Fragment是否可见
	 */
	public void setVisibleToUser(boolean visible) {
		debug("Visible: " + visible + "");
	}

	/*
	 * 窗口大小变化
     */
	public void onWindowVisibleSizeChange(Rect rect) {}

	public void onNewIntent(Intent intent) {}

	/*
     * 获得系统服务
     */
	public Object getSystemService(String name) {
		IServiceManager manager = ServiceProvider.getServiceProvider()
				.getServiceManager();
		if (manager != null) {
			Object service = manager.getService(name);
			if (service != null)
				return service;
		}
		if (getActivity() == null)
			return null;

		return getActivity().getSystemService(name);
	}
}
