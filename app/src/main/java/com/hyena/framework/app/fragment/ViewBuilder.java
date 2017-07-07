/**
 * Copyright (C) 2015 The AndroidSupport Project
 */
package com.hyena.framework.app.fragment;

import com.hyena.framework.app.widget.EmptyView;
import com.hyena.framework.app.widget.LoadingView;
import com.hyena.framework.app.widget.TitleBar;

/**
 * 通用View构造器
 * @author yangzc on 15/8/20.
 */
public interface ViewBuilder {

    /*
     * 构筑通用标题栏
     */
    TitleBar buildTitleBar(BaseUIFragment<?> fragment);

    /*
     * 构筑通用EmptyView
     */
    EmptyView buildEmptyView(BaseUIFragment<?> fragment);

    /*
     * 构筑通用的LoadingView
     */
    LoadingView buildLoadingView(BaseUIFragment<?> fragment);
}
