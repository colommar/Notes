package com.github.colommar.missionsubexample.event.handler;

import com.github.colommar.missionsubexample.event.BaseEvent;
import com.github.colommar.missionsubexample.event.EventHandler;
import com.github.colommar.missionsubexample.event.TaskCompletedEvent;
import com.github.colommar.missionsubexample.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 活动事件处理器
 */
@Component
public class ActivityEventHandler implements EventHandler<TaskCompletedEvent> {
    
    @Autowired
    private ActivityService activityService;
    
    @Override
    public void handle(TaskCompletedEvent event) {
        System.out.println("活动事件处理器收到任务完成事件，任务ID: " + event.getTaskId());
        activityService.notifyFinished(event.getTaskId());
    }
    
    @Override
    public String getEventType() {
        return TaskCompletedEvent.EVENT_TYPE;
    }
} 