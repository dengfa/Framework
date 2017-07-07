/**
 * Copyright (C) 2015 The AndroidSupport Project
 */
package com.hyena.framework.error;

/**
 * @author yangzc on 15/8/20.
 */
public interface ErrorMap {

    /*
     * 根据错误码提示错误信息
     */
    String getErrorHint(String errorCode, String descript);

}
