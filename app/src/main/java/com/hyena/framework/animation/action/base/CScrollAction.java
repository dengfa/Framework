package com.hyena.framework.animation.action.base;

import android.graphics.Point;

import com.hyena.framework.animation.sprite.CActionNode;

/**
 * Created by yangzc on 17/4/18.
 */

public class CScrollAction extends CAction {

    protected CScrollAction() {
        super();
    }

    @Override
    public void start(CActionNode actionNode) {
        super.start(actionNode);
    }

    @Override
    public void stop() {
//        super.stop();
    }

    @Override
    public boolean isDone() {
        return false;
    }

    @Override
    public void update(float dt) {
    }

    private int mScrollX, mScrollY;
    /*
    scroll x, y 均为负值
     */
    public void update(float dt, int scrollX, int scrollY, int width, int height) {
        if (scrollX != mScrollX || scrollY != mScrollY) {
            Point point = actionNode.getPosition();
            onScroll(point, scrollX, scrollY, width, height);
            this.mScrollX = scrollX;
            this.mScrollY = scrollY;
        }
    }

    public void onScroll(Point point, int scrollX, int scrollY, int width, int height) {

    }

    @Override
    public void reset() {
        super.reset();
    }
}
