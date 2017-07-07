package com.hyena.framework.animation.action.base;

import com.hyena.framework.animation.sprite.CActionNode;

/**
 * 重复动作
 * @author yangzc
 * @version 1.0
 *
 */
public class CRepeatAction extends CAction {

	//内部动作
	private CIntervalAction mIntervalAction;
	//已经循环次数
	private volatile int mRepeatedTimes = 0;
	//循环总次数
	private int mTotalTimes = -1;
	
	protected CRepeatAction(CIntervalAction action, int times){
		this.mIntervalAction = action;
		this.mTotalTimes = times;
	}
	
	public static CRepeatAction create(CIntervalAction action){
		return new CRepeatAction(action, -1);
	}
	
	/*
	 * 创建重复动作
	 */
	public static CRepeatAction create(CIntervalAction action, int times){
		return new CRepeatAction(action, times);
	}
	
	@Override
	public void update(float dt) {
		super.update(dt);
		if(!mRunning || mIntervalAction == null)
			return;
		
		if(mIntervalAction != null){
			mIntervalAction.update(dt);
			
			if(mIntervalAction.isDone()){
				if(mTotalTimes > 0){
					if(mRepeatedTimes < mTotalTimes){
						mRepeatedTimes ++;
						mIntervalAction.reset();
					}
				}else{
					mIntervalAction.reset();
				}
			}
		}
	}
	
	@Override
	public boolean isDone() {
		if(mTotalTimes < 0){
			return false;
		}else{
			return mRepeatedTimes >= mTotalTimes;
		}
	}
	
	@Override
	public void start(CActionNode actionNode) {
		super.start(actionNode);
		mRepeatedTimes = 0;
		if(mIntervalAction != null)
			mIntervalAction.start(actionNode);
	}
	
	@Override
	public void stop() {
		super.stop();
		mRepeatedTimes = 0;
		if(mIntervalAction != null){
			mIntervalAction.stop();
		}
	}
	
	@Override
	public void reset(){
		mRepeatedTimes = 0;
		if(mIntervalAction != null){
			mIntervalAction.reset();
		}
	}
}
