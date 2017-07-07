package com.hyena.framework.servcie.event;

import android.text.TextUtils;

import java.util.Comparator;
import java.util.SortedMap;
import java.util.Stack;
import java.util.TreeMap;

/**
 * Created by yangzc on 17/5/25.
 */

public class EventChainServiceImpl implements EventChainService {

    private SortedMap<Object, Event> mEventMap = new TreeMap<Object, Event>(new Comparator<Object>() {
        @Override
        public int compare(Object o1, Object o2) {
            Event event1 = mEventMap.get(o1);
            Event event2 = mEventMap.get(o2);
            //优先级最高的放到前边
            return event2.getPriority() - event1.getPriority();
        }
    });

    @Override
    public void addEvent(Event event) {
        mEventMap.put(event.getTag(), event);
    }

    @Override
    public void removeEvent(Object tag) {
        mEventMap.remove(tag);
    }

    @Override
    public void removeEvent(Event event) {
        removeEvent(event.getTag());
    }

    @Override
    public void startNextEvent(final String group) {
        for(Object tag : mEventMap.keySet()) {
            Event event = mEventMap.get(tag);
            if (TextUtils.isEmpty(group) || TextUtils.equals(event.getGroup(), group)) {
                mEventMap.remove(tag);
                event.setNextListener(new Event.NextListener() {
                    @Override
                    public void onNext() {
                        startNextEvent(group);
                    }
                });
                event.onEvent();
                break;
            }
        }
    }

    @Override
    public Stack<Event> getEventsByGroup(String group) {
        Stack<Event> stack = new Stack<Event>();
        for(Object tag : mEventMap.keySet()) {
            Event event = mEventMap.get(tag);
            if (TextUtils.equals(event.getGroup(), group)) {
                stack.push(event);
            }
        }
        return stack;
    }

    @Override
    public Event getEventByTag(Object tag) {
        return mEventMap.get(tag);
    }

    @Override
    public Stack<Event> getAllEvents() {
        Stack<Event> stack = new Stack<Event>();
        for(Object tag : mEventMap.keySet()) {
            Event event = mEventMap.get(tag);
            stack.push(event);
        }
        return stack;
    }

    @Override
    public void releaseAll() {
        mEventMap.clear();
    }
}
