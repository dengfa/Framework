/**
 * Copyright (C) 2015 The AndroidSupport Project
 */
package com.hyena.framework.app.fragment;

import android.app.Activity;
import android.view.View;

import com.hyena.framework.servcie.navigate.NavigateService;

/**
 * Fragment基类
 * @author yangzc
 */
public class BaseSubFragment extends HSlidingBackFragment {

	private Activity        mAttachActivity;
	//父Fragment
	private BaseSubFragment mParentFragment;

	/*
	 * 设置主场景
	 */
	public BaseSubFragment setParent(Activity activity, BaseSubFragment parentFragment) {
		this.mAttachActivity = activity;
		this.mParentFragment = parentFragment;
		return this;
	}

	/*
	 * 获得父Fragment
	 */
	public BaseSubFragment getParent() {
		return mParentFragment;
	}

	@Override
	public void onPanelSlide(View pPanel, float pSlideOffset) {
		super.onPanelSlide(pPanel, pSlideOffset);
	}

	/*
	 * 显示子场景
	 */
	public void showFragment(BaseSubFragment fragment) {
        checkNavigateController();
		NavigateService navigateService = (NavigateService) getSystemService(NavigateService.SERVICE_NAME);
		if (navigateService != null) {
			fragment.setParent(mAttachActivity, this);
			navigateService.addSubFragment(fragment);
		}
	}

    @Override
    public void onDestroyViewImpl() {
        super.onDestroyViewImpl();
    }

    @Override
	public void onPanelClosed(View pPanel) {
    	finishWithOutAnim();
		super.onPanelClosed(pPanel);
	}

	/**
	 * 清空所有Fragment
	 */
	public void removeAllFragment() {
        checkNavigateController();
		NavigateService navigateService = (NavigateService) getSystemService(NavigateService.SERVICE_NAME);
		if (navigateService != null) {
			navigateService.removeAllFragment();
		}
	}

    /**
     * 检查导航控制器是否合法
     */
	protected void checkNavigateController() {
		//do nothing
	}
	
	/**
	 * 无动画退出窗口
	 */
	public void finishWithOutAnim(){
		checkNavigateController();
		NavigateService service = (NavigateService) getSystemService(NavigateService.SERVICE_NAME);
		if (service != null) {
			service.removeSubFragment(this);
		}
	}

	@Override
	public Object getSystemService(String name) {
		Object service = super.getSystemService(name);
		if (service == null && mAttachActivity != null) {
			service = mAttachActivity.getSystemService(name);
		}
		return service;
	}
}
