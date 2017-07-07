/**
 * Copyright (C) 2015 The AndroidSupport Project
 */
package com.hyena.framework.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Toast通用类
 * @author yangzc on 15/8/20.
 */
public class ToastUtils {

    /*
     * 显示Toast
     */
    public static void showToast(Context context, String text){
        Toast.makeText(context, text, Toast.LENGTH_LONG).show();
    }

    /*
     * 显示短Toast
     */
    public static void showShortToast(Context context, String text){
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }

    /*
     * 显示Toast
     */
    public static void showToast(Context context, int resId){
        Toast.makeText(context, resId, Toast.LENGTH_LONG).show();
    }

    /*
     * 显示短Toast
     */
    public static void showShortToast(Context context, int resId){
        Toast.makeText(context, resId, Toast.LENGTH_SHORT).show();
    }
}
