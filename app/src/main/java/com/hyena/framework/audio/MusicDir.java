/**
 * Copyright (C) 2015 The AndroidSupport Project
 */
package com.hyena.framework.audio;

import com.hyena.framework.config.FrameworkConfig;
import com.hyena.framework.utils.BaseFileUtils;

import java.io.File;

/**
 * 音乐缓存
 * @author yangzc on 15/8/24.
 */
public class MusicDir {

    /**
     * 获得音乐缓存目录
     * @return 歌曲目录
     */
    public static File getMusicDir(){
        return BaseFileUtils.getDir(FrameworkConfig.getConfig().getAppRootDir(), "music");
    }

}
