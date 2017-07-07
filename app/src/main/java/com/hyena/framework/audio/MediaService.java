/**
 * Copyright (C) 2015 The AndroidSupport Project
 */
package com.hyena.framework.audio;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;

import com.hyena.framework.audio.bean.Song;
import com.hyena.framework.audio.player.BasePlayer.OnPlayStateChangeListener;
import com.hyena.framework.clientlog.LogUtil;

/**
 * 媒体播放服务
 * @author yangzc
 */
public class MediaService extends Service {

	private static final String  TAG   = "MediaService";
	private static final boolean DEBUG = true;
	
	public static final int CMD_PLAY = 0;//播放歌曲
	public static final int CMD_RESUME = 1;//还原播放
	public static final int CMD_PAUSE = 2;//暂停
	public static final int CMD_SEEK = 3;//seekTo
	public static final int CMD_REQUEST_POSITION = 4;//获得播放位置
	public static final int CMD_RESET_SONG = 5;//还原到初始状态
	
	//回调消息
	private static final int MSG_REFRESH_START_CODE = 100;
//	public static final int MSG_REFRESH_PLAY_PROGRESS = MSG_REFRESH_START_CODE +1;//刷新播放进度
//	public static final int MSG_REFRESH_DOWNLOAD_PROGRESS = MSG_REFRESH_START_CODE +2;//刷新加载进度
	public static final int MSG_REFRESH_PLAY_STATUS_CHANGE = MSG_REFRESH_START_CODE + 3;//播放状态改变

	public static final int ACTION_SAY_HI = -1;
	//播放服务事件
	public static final int ACTION_SERVICE_PLAY_STATUS_EVENT = 0;
	
	//播放器
	private MusicPlayer mMusicPlayer;
	private Messenger   mClientMessenger;
	private Messenger   mLocalMessenger;
	private HandlerThread mIoHandlerThread = null;
	
//	private MediaPlayServiceHelper mPlayServiceHelper;//播放相关帮助类
	
	@Override
	public IBinder onBind(Intent intent) {
		if(mLocalMessenger == null){
			mLocalMessenger = new Messenger(new Handler(mIoHandlerThread.getLooper()){
				@Override
				public void handleMessage(Message msg) {
					super.handleMessage(msg);
					handleClientMessageImpl(msg);
				}
			});
		}
		return mLocalMessenger.getBinder();
	}
	
	@Override
	public void onCreate() {
		super.onCreate();
		mIoHandlerThread = new HandlerThread("mediaplayer");
		mIoHandlerThread.start();
		//初始化
//		mPlayServiceHelper = new MediaPlayServiceHelper();
		
		//初始化音乐播放器
		mMusicPlayer = new MusicPlayer(mIoHandlerThread.getLooper());
		mMusicPlayer.setOnPlayStateChangeListener(mPlayStateChangeListener);
//		mMusicPlayer.setOnPlayPositionChangeListener(mPlayPositionChangeListener);
	}
	
	private OnPlayStateChangeListener mPlayStateChangeListener = new OnPlayStateChangeListener() {
		
		@Override
		public void onPlayStateChange(int state) {
			if(DEBUG)
				LogUtil.v(TAG,  "player status: " + StatusCode.getStatusLabel(state));
			
			//通知播放信息改变
			if (mClientMessenger != null) {
				try {
					Message msg = new Message();
					msg.what = ACTION_SERVICE_PLAY_STATUS_EVENT;
					msg.obj = MediaPlayServiceHelper.buildPlayStatusChangeBundle(mMusicPlayer
							.getCurrentSong(), state);
					mClientMessenger.send(msg);
				} catch (RemoteException e) {
					e.printStackTrace();
				}
			}
		}
	};

//	private BasePlayer.OnPlayPositionChangeListener mPlayPositionChangeListener = new BasePlayer.OnPlayPositionChangeListener() {
//		@Override
//		public void onPositionChange(long position, long duration) {
			//通知播放进度
//			if(mPlayServiceHelper != null) {
//				mPlayServiceHelper.notifyPlayProgressChange(mMusicPlayer.getCurrentSong(), position, duration);
//			}
//		}
//	};
	
	/*
	 * 处理客户端请求
	 */
	private void handleClientMessageImpl(Message msg) {
		int what = msg.what;
		if(DEBUG){
			LogUtil.v(TAG,  "client cmd: " + what);
		}
		switch (what) {
		case ACTION_SAY_HI: {
			this.mClientMessenger = msg.replyTo;
			if(DEBUG) {
				LogUtil.v(TAG, "say hi from client.");
			}
			try {
				sayHi();
			} catch (RemoteException e) {
				e.printStackTrace();
			}
			break;
		}
		case CMD_PLAY: {//播放歌曲
			msg.getData().setClassLoader(getClassLoader());
			Song song = (Song) msg.getData().getSerializable("song");
			playImpl(song);
			break;
		}
		case CMD_RESUME: {//还原播放
			resumeImpl();
			break;
		}
		case CMD_PAUSE: {//暂停
			pauseImpl();
			break;
		}
		case CMD_SEEK: {//seekTo
			if (mMusicPlayer != null) {
				try {
					mMusicPlayer.seekTo(msg.arg1);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			break;
		}
		case CMD_REQUEST_POSITION: {
			try {
				Message response = new Message();
				response.arg1 = (int) mMusicPlayer.getPosition();
				response.arg2 = (int) mMusicPlayer.getDuration();
				msg.replyTo.send(response);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
			break;
		}
		case CMD_RESET_SONG: {
			if (mMusicPlayer != null) {
				mMusicPlayer.resetSong();
			}
			break;
		}
		default:
			break;
		}
	}

	private void sayHi() throws RemoteException {
		Message msg = new Message();
		msg.what = MediaService.ACTION_SAY_HI;
		msg.replyTo = mClientMessenger;
		if (mClientMessenger != null)
			mClientMessenger.send(msg);
	}
	
	/**
	 * 暂停播放
	 */
	private void pauseImpl(){
		if(mMusicPlayer != null){
			mMusicPlayer.pause();
		}	
	}
	
	/**
	 * 恢复播放
	 */
	private void resumeImpl(){
		if(mMusicPlayer != null){
			mMusicPlayer.resume();
		}
	}
	
	/**
	 * 开始播放歌曲
	 * @param song 歌曲
	 */
	private void playImpl(Song song){
		if(mMusicPlayer != null){
			mMusicPlayer.playSong(song);
		}
	}
}
