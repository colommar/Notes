package com.github.colommar.missionsubexample.event;

/**
 * 任务过期事件
 */
public class TaskExpiredEvent extends BaseEvent {
    
    public static final String EVENT_TYPE = "TASK_EXPIRED";
    
    public TaskExpiredEvent(Long taskId, Object data) {
        super(EVENT_TYPE, taskId, data);
    }
} 