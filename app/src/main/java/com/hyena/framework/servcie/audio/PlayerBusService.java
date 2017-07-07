/**
 * Copyright (C) 2014 The plugin_music Project
 */
package com.hyena.framework.servcie.audio;

import com.hyena.framework.audio.bean.Song;
import com.hyena.framework.servcie.BaseService;
import com.hyena.framework.servcie.bus.IBusServiceStatusListener;

/**
 * @author yangzc
 * @version 1.0
 * 总线服务
 */
public interface PlayerBusService extends IBusServiceStatusListener, BaseService {

	String BUS_SERVICE_NAME = "player_bus";
	
	/*
	 * 播放
	 */
	void play(Song song) throws Exception;
	
	/*
	 * 暂停
	 */
	void pause() throws Exception;

	/*
	 * 重新播放
     */
	void resume() throws Exception;

	/*
	 * seekTo
     */
	void seekTo(long position) throws Exception;

	/*
	 * get position
     */
	void getPosition() throws Exception;

	/*
	 * resetSong
	 */
	void resetSong() throws Exception;
	
	/*
	 * 获得播放Bus观察者
	 */
	PlayerBusServiceObserver getPlayerBusServiceObserver();
}
