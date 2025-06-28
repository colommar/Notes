package com.github.colommar.missionsubexample.event;

import org.springframework.stereotype.Component;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.List;
import java.util.Map;

/**
 * 事件总线 - 发布-订阅中心
 */
@Component
public class EventBus {
    
    /**
     * 事件类型 -> 处理器列表的映射
     */
    private final Map<String, List<EventHandler>> handlers = new ConcurrentHashMap<>();
    
    /**
     * 注册事件处理器
     * @param eventType 事件类型
     * @param handler 事件处理器
     */
    public void subscribe(String eventType, EventHandler handler) {
        handlers.computeIfAbsent(eventType, k -> new CopyOnWriteArrayList<>()).add(handler);
        System.out.println("注册事件处理器: " + eventType + " -> " + handler.getClass().getSimpleName());
    }
    
    /**
     * 取消注册事件处理器
     * @param eventType 事件类型
     * @param handler 事件处理器
     */
    public void unsubscribe(String eventType, EventHandler handler) {
        List<EventHandler> handlerList = handlers.get(eventType);
        if (handlerList != null) {
            handlerList.remove(handler);
            System.out.println("取消注册事件处理器: " + eventType + " -> " + handler.getClass().getSimpleName());
        }
    }
    
    /**
     * 发布事件
     * @param event 事件对象
     */
    public void publish(BaseEvent event) {
        String eventType = event.getEventType();
        List<EventHandler> handlerList = handlers.get(eventType);
        
        if (handlerList != null && !handlerList.isEmpty()) {
            System.out.println("发布事件: " + eventType + " (任务ID: " + event.getTaskId() + ")");
            
            for (EventHandler handler : handlerList) {
                try {
                    handler.handle(event);
                } catch (Exception e) {
                    System.err.println("事件处理器执行失败: " + handler.getClass().getSimpleName() + " -> " + e.getMessage());
                }
            }
        } else {
            System.out.println("没有找到事件处理器: " + eventType);
        }
    }
    
    /**
     * 获取指定事件类型的处理器数量
     * @param eventType 事件类型
     * @return 处理器数量
     */
    public int getHandlerCount(String eventType) {
        List<EventHandler> handlerList = handlers.get(eventType);
        return handlerList != null ? handlerList.size() : 0;
    }
    
    /**
     * 清空所有处理器
     */
    public void clear() {
        handlers.clear();
        System.out.println("清空所有事件处理器");
    }
} 