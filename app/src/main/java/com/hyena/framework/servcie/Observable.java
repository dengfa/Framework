/*
 * Copyright (C) 2016 The AndroidSupport Project
 */

package com.hyena.framework.servcie;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yangzc on 16/11/18.
 */
public class Observable<T> {

    private List<Observer<T>> mObservers;

    public void addObserver(Observer<T> listener) {
        if (mObservers == null)
            mObservers = new ArrayList<Observer<T>>();

        if (!mObservers.contains(listener))
            mObservers.add(listener);
    }

    public void removeObserver(Observer<T> listener) {
        if (mObservers == null)
            return;
        mObservers.remove(listener);
    }

    public void notifyDataChange(T data) {
        if (mObservers == null)
            return;

        for (Observer<T> listener : mObservers) {
            listener.onDataChange(data);
        }
    }

    public interface Observer<T> {
        void onDataChange(T t);
    }

}
