/**
 * Copyright (C) 2014 The plugin_music Project
 */
package com.hyena.framework.servcie.bus;

import android.os.Bundle;
import android.os.Messenger;

import com.hyena.framework.audio.bean.Song;

/**
 * @author yangzc
 * @version 1.0
 * 服务状态监听器
 */
public interface IBusServiceStatusListener {

	/*
	 * 收到服务消息
	 */
	void onReceiveServiceAction(int type, Song song, Bundle bundle);
	
	/*
	 * 连接到服务
	 */
	void onServiceConnected(Messenger messenger);

	/*
	 * 与服务断开连接
	 */
	void onServiceDisConnected();
}
