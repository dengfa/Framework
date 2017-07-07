/**
 * Copyright (C) 2014 The plugin_music Project
 */
package com.hyena.framework.servcie.audio.listener;

import com.hyena.framework.audio.bean.Song;

/**
 * @author yangzc
 * @version 1.0
 * 播放状态改变回调
 */
public interface PlayStatusChangeListener {

	void onStatusChange(Song song, int status);
	
}
