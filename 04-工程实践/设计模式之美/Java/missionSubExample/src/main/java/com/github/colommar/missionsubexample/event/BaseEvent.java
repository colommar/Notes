package com.github.colommar.missionsubexample.event;

import java.time.LocalDateTime;

/**
 * 事件基类
 */
public abstract class BaseEvent {
    
    /**
     * 事件ID
     */
    private String eventId;
    
    /**
     * 事件类型
     */
    private String eventType;
    
    /**
     * 事件发生时间
     */
    private LocalDateTime timestamp;
    
    /**
     * 任务ID
     */
    private Long taskId;
    
    /**
     * 事件数据
     */
    private Object data;
    
    public BaseEvent(String eventType, Long taskId, Object data) {
        this.eventId = java.util.UUID.randomUUID().toString();
        this.eventType = eventType;
        this.timestamp = LocalDateTime.now();
        this.taskId = taskId;
        this.data = data;
    }
    
    // Getters and Setters
    public String getEventId() {
        return eventId;
    }
    
    public void setEventId(String eventId) {
        this.eventId = eventId;
    }
    
    public String getEventType() {
        return eventType;
    }
    
    public void setEventType(String eventType) {
        this.eventType = eventType;
    }
    
    public LocalDateTime getTimestamp() {
        return timestamp;
    }
    
    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
    
    public Long getTaskId() {
        return taskId;
    }
    
    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }
    
    public Object getData() {
        return data;
    }
    
    public void setData(Object data) {
        this.data = data;
    }
} 