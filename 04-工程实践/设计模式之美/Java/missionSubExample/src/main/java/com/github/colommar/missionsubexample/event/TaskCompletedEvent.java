package com.github.colommar.missionsubexample.event;

/**
 * 任务完成事件
 */
public class TaskCompletedEvent extends BaseEvent {
    
    public static final String EVENT_TYPE = "TASK_COMPLETED";
    
    public TaskCompletedEvent(Long taskId, Object data) {
        super(EVENT_TYPE, taskId, data);
    }
} 