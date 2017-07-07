/**
 * Copyright (C) 2015 The AppFramework Project
 */
package com.hyena.framework.annotation;


/**
 * 布局动画
 * @author yangzc
 */
public @interface LayoutAnimation {

	/*
	 * 执行时间
	 */
    int duration() default 200;
    
    /*
     * 开始的X位置
     */
    int offsetX();
    
    /*
     * 开始的Y位置
     */
    int offsetY();
}
