/*
 * Copyright (C) 2016 The AndroidSupport Project
 */

package com.hyena.framework.servcie.navigate;

import com.hyena.framework.app.fragment.BaseFragment;
import com.hyena.framework.app.fragment.BaseUIFragmentHelper;
import com.hyena.framework.servcie.BaseService;

/**
 * Created by yangzc on 16/12/5.
 */
public interface NavigateService extends BaseService {

    String SERVICE_NAME = "navigate_svs";

    /*
	 * 添加子Fragment
	 */
    void addSubFragment(BaseFragment fragment);

    /*
     * 移除Fragment
     */
    void removeSubFragment(BaseFragment fragment);

    /*
     * 清空所有的Fragment
     */
    void removeAllFragment();

    /*
     * 获得UI帮助类
     */
    <T extends BaseUIFragmentHelper> T getUIFragmentHelper(BaseFragment fragment);
}
