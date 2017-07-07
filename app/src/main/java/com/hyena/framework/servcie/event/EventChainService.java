package com.hyena.framework.servcie.event;

import com.hyena.framework.servcie.BaseService;

import java.util.Stack;

/**
 * Created by yangzc on 17/5/25.
 */

public interface EventChainService extends BaseService {

    String SERVICE_NAME = "svs_event_chain";

    /**
     * 添加新的事件
     * @param event 事件
     */
    void addEvent(Event event);

    /**
     * 删除事件
     * @param tag 事件标签
     */
    void removeEvent(Object tag);

    /**
     * 删除事件
     * @param event 事件
     */
    void removeEvent(Event event);

    /**
     * 通过事件组获得事件列表，事件根据优先级排序
     * @param group 事件组
     * @return 事件堆栈
     */
    Stack<Event> getEventsByGroup(String group);

    /**
     * 根据事件标签获得事件
     * @param tag 标签
     * @return 事件
     */
    Event getEventByTag(Object tag);

    /**
     * 获取分组中最高优先级的事件
     * @param group
     */
    void startNextEvent(String group);

    /**
     * 获得所有事件 根据优先级排序
     * @return 事件堆栈
     */
    Stack<Event> getAllEvents();

}
