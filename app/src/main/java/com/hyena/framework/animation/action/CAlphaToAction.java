package com.hyena.framework.animation.action;

import com.hyena.framework.animation.action.base.CIntervalAction;
import com.hyena.framework.animation.sprite.CActionNode;

import android.view.animation.Interpolator;

public class CAlphaToAction extends CIntervalAction {

	private int mStartAlpha = 255;
	private int mEndAlpha = 255;
	
	protected CAlphaToAction(int fromAlpha, int alpha, float d, Interpolator interpolator) {
		super(d);
		this.mStartAlpha = fromAlpha;
		this.mEndAlpha = alpha;
		
		if(interpolator != null)
			setInterpolator(interpolator);
	}
	
	/*
	 * 创建渐变动作
	 */
	public static CAlphaToAction create(int fromAlpha, int alpha, float d, Interpolator interpolator){
		return new CAlphaToAction(fromAlpha, alpha, d, interpolator);
	}
	
	/*
	 * 创建渐变动作
	 */
	public static CAlphaToAction create(int fromAlpha, int alpha, float d){
		return new CAlphaToAction(fromAlpha, alpha, d, null);
	}
	
	public static CAlphaToAction create(int alpha, float d){
		return new CAlphaToAction(255, alpha, d, null);
	}
	
	@Override
	public void update(float dt) {
		super.update(dt);
		if(!mRunning || actionNode == null || isDone()){
			return;
		}

		float elapsePercent = getElapsePercent();
		int alpha = (int) ((mEndAlpha - mStartAlpha)* (elapsePercent + 0.5f)) + mStartAlpha;
		if(alpha < 0){
			alpha = 0;
		}else if(alpha > 255){
			alpha = 255;
		}
		actionNode.setAlpha(alpha);
	}
	
	@Override
	public void start(CActionNode actionNode) {
		super.start(actionNode);
	}
}
