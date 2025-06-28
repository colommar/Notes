package com.github.colommar.missionsubexample.event;

import com.github.colommar.missionsubexample.event.handler.ActivityEventHandler;
import com.github.colommar.missionsubexample.event.handler.TaskManagerEventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * 事件注册器 - 自动注册所有事件处理器
 */
@Component
public class EventRegistry implements CommandLineRunner {
    
    @Autowired
    private EventBus eventBus;
    
    @Autowired
    private ActivityEventHandler activityEventHandler;
    
    @Autowired
    private TaskManagerEventHandler taskManagerEventHandler;
    
    @Override
    public void run(String... args) throws Exception {
        // 注册事件处理器
        registerEventHandlers();
        System.out.println("事件处理器注册完成");
    }
    
    /**
     * 注册所有事件处理器
     */
    private void registerEventHandlers() {
        // 注册任务完成事件处理器
        eventBus.subscribe(TaskCompletedEvent.EVENT_TYPE, activityEventHandler);
        eventBus.subscribe(TaskCompletedEvent.EVENT_TYPE, taskManagerEventHandler);
        
        // 可以在这里注册更多事件处理器
        // eventBus.subscribe(TaskPausedEvent.EVENT_TYPE, someHandler);
        // eventBus.subscribe(TaskExpiredEvent.EVENT_TYPE, someHandler);
    }
} 