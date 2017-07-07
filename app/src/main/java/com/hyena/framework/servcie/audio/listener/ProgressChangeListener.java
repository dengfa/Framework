/**
 * Copyright (C) 2014 The plugin_music Project
 */
package com.hyena.framework.servcie.audio.listener;


/**
 * @author yangzc
 * @version 1.0
 * 播放进度改变
 */
public interface ProgressChangeListener {

	/*
	 * 播放进度改变
	 */
	void onPlayProgressChange(long progress, long duration);
	
	/*
	 * 加载进度改变
	 */
	void onDownloadProgressChange(int percent, long duration);
}
