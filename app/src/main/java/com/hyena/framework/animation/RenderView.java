/**
 * Copyright (C) 2014 The AndroidSupport Project
 */
package com.hyena.framework.animation;

import android.content.Context;
import android.graphics.Rect;
import android.view.View.OnTouchListener;

/**
 * @author yangzc
 * @version 1.0
 */
public interface RenderView {

	void setDirector(Director director);

	/**
	 * 开始刷新
	 */
	void startRefresh();
	
	/**
	 * 停止刷新
	 */
	void stopRefresh();

	void forceRefresh();
	
	/*
	 * 获得View上下文
	 */
	Context getContext();
	
	/*
	 * 渲染器是否显示
	 */
	boolean isShown();
	
	/*
	 * 设置触摸监听事件
	 */
	void setOnTouchListener(OnTouchListener listener);
	
	/*
	 * 设置窗口大小改变事件
	 */
	void setSizeChangeListener(SizeChangeListener listener);
	
	interface SizeChangeListener {
		void onSizeChange(Rect rect);
	}
}
