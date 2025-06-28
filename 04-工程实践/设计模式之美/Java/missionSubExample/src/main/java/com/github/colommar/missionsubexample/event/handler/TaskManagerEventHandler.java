package com.github.colommar.missionsubexample.event.handler;

import com.github.colommar.missionsubexample.event.BaseEvent;
import com.github.colommar.missionsubexample.event.EventHandler;
import com.github.colommar.missionsubexample.event.TaskCompletedEvent;
import com.github.colommar.missionsubexample.service.TaskManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 任务管理器事件处理器
 */
@Component
public class TaskManagerEventHandler implements EventHandler<TaskCompletedEvent> {
    
    @Autowired
    private TaskManager taskManager;
    
    @Override
    public void handle(TaskCompletedEvent event) {
        System.out.println("任务管理器事件处理器收到任务完成事件，任务ID: " + event.getTaskId());
        taskManager.release(event.getTaskId());
    }
    
    @Override
    public String getEventType() {
        return TaskCompletedEvent.EVENT_TYPE;
    }
} 