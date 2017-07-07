package com.hyena.framework.animation.action.base;

import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;

import com.hyena.framework.animation.sprite.CActionNode;

/**
 * 时间片动作
 * @author yangzc
 */
public class CIntervalAction extends CFiniteTimeAction {
	
	protected volatile float elapsed;
	protected volatile boolean firstTick;
	protected volatile Interpolator mInterpolator = new LinearInterpolator();

    /*
     * 获得已经使用时间
     */
    public float getElapsed() {
        return elapsed;
    }

    protected CIntervalAction(float d) {
        super(d);
        elapsed = 0.0f;
        firstTick = true;
    }

    @Override
    public synchronized boolean isDone() {
        return super.isDone() || (elapsed >= mDuration);
    }
    
    @Override
    public void stop() {
    	super.stop();
    }
    
    @Override
    public synchronized void update(float dt) {
    	super.update(dt);
    	if (firstTick) {
            firstTick = false;
            elapsed = 0;
        } else
            elapsed += dt;
    }

    @Override
    public synchronized void start(CActionNode actionNode) {
        super.start(actionNode);
        elapsed = 0.0f;
        firstTick = true;
    }
    
    public synchronized void reset(){
        elapsed = 0.0f;
        firstTick = true;
    }
    
    /*
     * 获得度过百分比
     */
    public float getElapsePercent(){
    	if(mInterpolator != null){
    		float result = mInterpolator.getInterpolation(getElapsed()/getDuration());
//    		if(result <=0)
//    			return 0;
//    		if(result > 1)
//    			return 1;
    		
    		return result;
    	}
    	return getElapsed()/getDuration();
    }
    
    /*
     * 设置插入器
     */
    public void setInterpolator(Interpolator interpolator){
    	this.mInterpolator = interpolator;
    }
}
