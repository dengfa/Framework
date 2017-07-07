/**
 * Copyright (C) 2014 The AndroidSupport Project
 */
package com.hyena.framework.animation.action;

import com.hyena.framework.animation.action.base.CIntervalAction;

/**
 * @author yangzc
 * @version 1.0
 *
 */
public class CDelayAction extends CIntervalAction {

	protected CDelayAction(float d) {
		super(d);
	}

	public static CDelayAction create(float delay){
		return new CDelayAction(delay);
	}
	
	@Override
	public synchronized void update(float dt) {
		super.update(dt);
	}
	
	@Override
	public synchronized boolean isDone() {
		return super.isDone();
	}
}
