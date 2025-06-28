package com.github.colommar.missionsubexample.event;

/**
 * 任务暂停事件
 */
public class TaskPausedEvent extends BaseEvent {
    
    public static final String EVENT_TYPE = "TASK_PAUSED";
    
    public TaskPausedEvent(Long taskId, Object data) {
        super(EVENT_TYPE, taskId, data);
    }
} 