/**
 * Copyright (C) 2015 The AndroidSupport Project
 */
package com.hyena.framework.audio.bean;

import android.text.TextUtils;

import com.hyena.framework.audio.MusicDir;
import com.hyena.framework.security.MD5Util;

import java.io.File;
import java.io.Serializable;

/**
 * 歌曲信息
 *
 * @author yangzc
 */
public class Song implements Serializable {

    // 歌曲远程URL
    private String  mUrl;
    private String  mLocalPath;
    private boolean mIsOnline;

    public Song(boolean isOnline, String url, String localPath) {
        this.mUrl = url;
        this.mLocalPath = localPath;
        this.mIsOnline = isOnline;
    }

    /*
     * 获得本地路径
     */
    public File getLocalFile() {
        if(TextUtils.isEmpty(mLocalPath)) {
            return new File(MusicDir.getMusicDir(), MD5Util.encode(mUrl)
                + ".mp3");
        }
        return new File(mLocalPath);
    }

    public String getUrl() {
        return mUrl;
    }

    /*
     * 是否是在线歌曲
     */
    public boolean isOnline() {
        return mIsOnline;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Song) {
            if (mUrl != null)
                return mUrl.equals(((Song) o).mUrl);
        }
        return super.equals(o);
    }
}
