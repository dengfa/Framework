package com.hyena.framework.servcie.event;

/**
 * Created by yangzc on 17/5/25.
 */

public abstract class Event {

    /**
     * 发生事件
     */
    abstract void onEvent();

    private int    mPriority;
    private String mGroup;
    private Object mTag;

    public Event(Object tag, String group, int priority) {
        this.mTag = tag;
        this.mGroup = group;
        this.mPriority = priority;
    }

    protected Object getTag() {
        return mTag;
    }

    protected String getGroup() {
        return mGroup;
    }

    protected int getPriority() {
        return mPriority;
    }

    public void next() {
        if (mNextListener != null) {
            mNextListener.onNext();
        }
    }

    private NextListener mNextListener;
    public void setNextListener(NextListener listener) {
        this.mNextListener = listener;
    }
    interface NextListener {
        void onNext();
    }
}
