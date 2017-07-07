/**
 * Copyright (C) 2015 The AndroidSupport Project
 */
package com.hyena.framework.app.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import com.hyena.framework.app.fragment.BaseUIFragment;

/**
 * LoadingView
 * @author yangzc
 *
 */
public abstract class LoadingView extends RelativeLayout {

	private BaseUIFragment<?> mBaseUIFragment;

	public LoadingView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	public LoadingView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public LoadingView(Context context) {
		super(context);
		init();
	}
	
	private void init(){
		setClickable(true);
	}

	public void setBaseUIFragment(BaseUIFragment<?> baseUIFragment){
		this.mBaseUIFragment = baseUIFragment;
	}

	public BaseUIFragment<?> getBaseUIFragment(){
		return mBaseUIFragment;
	}
	
	/**
	 * 显示Loading
	 */
	public void showLoading(){
		showLoading("正在加载中...");
	}
	
	/*
	 * 设置据上方距离
	 */
	public void setTopMargin(int topMargin){
		RelativeLayout.LayoutParams params = (LayoutParams) getLayoutParams();
		params.topMargin = topMargin;
		requestLayout();
	}

	/*
	 * 显示Loading
	 */
	public abstract void showLoading(String hint);


}
