package com.github.colommar.missionsubexample.event;

/**
 * 事件处理器接口
 */
public interface EventHandler<T extends BaseEvent> {
    
    /**
     * 处理事件
     * @param event 事件对象
     */
    void handle(T event);
    
    /**
     * 获取支持的事件类型
     * @return 事件类型
     */
    String getEventType();
} 