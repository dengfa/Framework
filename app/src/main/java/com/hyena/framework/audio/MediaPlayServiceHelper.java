/**
 * Copyright (C) 2014 The plugin_music Project
 */
package com.hyena.framework.audio;

import android.os.Bundle;

import com.hyena.framework.audio.bean.Song;

/**
 * @author yangzc
 * @version 1.0
 * @createTime 2014年11月12日 上午10:53:50
 * 
 */
class MediaPlayServiceHelper {

	/*
	 * 通知播放进度改变
	 */
//	public void notifyPlayProgressChange(Song song, long progress, long duration){
//		Intent intent = buildCommonMsgIntent(song, MediaService.MSG_REFRESH_PLAY_PROGRESS);
//		intent.putExtra("play_progress", progress);
//		intent.putExtra("duration", duration);
//		MsgCenter.sendGlobalBroadcast(intent);
//	}
	
	/**
	 * 通知加载进度改变
	 * @param song
	 * @param progress
	 * @param duration
	 */
//	public void notifyDownloadProgressChange(Song song, int progress, long duration){
//		Intent intent = buildCommonMsgIntent(song, MediaService.MSG_REFRESH_DOWNLOAD_PROGRESS);
//		intent.putExtra("load_progress", progress);
//		intent.putExtra("duration", duration);
//		MsgCenter.sendGlobalBroadcast(intent);
//	}
	
	/**
	 * 事件通知
	 * @param song
	 * @param type 数据类型
	 * @param values
	 */
//	public void notifyEvt(Song song, int type, List<KeyValuePair> values){
//		Intent intent = buildCommonMsgIntent(song, type);
//		for(int i=0; i< values.size(); i++){
//			KeyValuePair pair = values.get(i);
//			intent.putExtra(pair.getKey(), pair.getValue());
//		}
//		MsgCenter.sendGlobalBroadcast(intent);
//	}

	/**
	 * 构造播放状态改变Bundle
	 * @param song
	 * @param status StatusCode
	 */
	public static Bundle buildPlayStatusChangeBundle(Song song, int status){
		Bundle bundle = buildCommonBundle(song, MediaService.MSG_REFRESH_PLAY_STATUS_CHANGE);
		bundle.putInt("status", status);
		return bundle;
	}
	
	/**
	 * 构造通用Bundle
	 * @param song
	 * @param type
	 * @return
	 */
	private static Bundle buildCommonBundle(Song song, int type) {
		Bundle bundle = new Bundle();
		bundle.putSerializable("song", song);
		bundle.putInt("type", type);
		return bundle;
	}
}
