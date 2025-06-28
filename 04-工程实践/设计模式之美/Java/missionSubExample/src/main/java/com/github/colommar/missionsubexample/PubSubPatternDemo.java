package com.github.colommar.missionsubexample;

import com.github.colommar.missionsubexample.enums.ActionType;
import com.github.colommar.missionsubexample.event.EventBus;
import com.github.colommar.missionsubexample.event.TaskCompletedEvent;
import com.github.colommar.missionsubexample.model.Task;
import com.github.colommar.missionsubexample.state.TaskInit;
import com.github.colommar.missionsubexample.state.TaskOngoingV2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 发布-订阅模式演示类
 */
@Component
public class PubSubPatternDemo {
    
    @Autowired
    private TaskInit taskInit;
    
    @Autowired
    private TaskOngoingV2 taskOngoingV2;
    
    @Autowired
    private EventBus eventBus;
    
    /**
     * 演示发布-订阅模式
     */
    public void demonstratePubSubPattern() {
        System.out.println("=== 发布-订阅模式演示 ===");
        
        // 创建任务
        Task task = new Task();
        task.setTaskId(2001L);
        task.setTaskName("学习发布-订阅模式");
        task.setDescription("通过实际代码学习发布-订阅模式的应用");
        
        // 设置初始状态
        task.setState(taskInit);
        
        System.out.printf("任务创建完成: ID=%d, 名称=%s%n", 
            task.getTaskId(), task.getTaskName());
        System.out.printf("当前状态: %s%n", task.getCurrentStateName());
        
        try {
            // 开始任务
            System.out.println("\n--- 开始任务 ---");
            task.executeAction(ActionType.START);
            System.out.printf("当前状态: %s%n", task.getCurrentStateName());
            
            // 完成任务（会触发事件发布）
            System.out.println("\n--- 完成任务 ---");
            task.executeAction(ActionType.ACHIEVE);
            System.out.printf("当前状态: %s%n", task.getCurrentStateName());
            
        } catch (Exception e) {
            System.err.println("执行过程中发生错误: " + e.getMessage());
        }
        
        System.out.println("\n=== 演示结束 ===");
    }
    
    /**
     * 演示动态订阅
     */
    public void demonstrateDynamicSubscription() {
        System.out.println("\n=== 动态订阅演示 ===");
        
        // 创建自定义事件处理器
        CustomEventHandler customHandler = new CustomEventHandler();
        
        // 动态订阅事件
        eventBus.subscribe(TaskCompletedEvent.EVENT_TYPE, customHandler);
        System.out.println("动态注册了自定义事件处理器");
        
        // 创建任务并完成
        Task task = new Task();
        task.setTaskId(2002L);
        task.setTaskName("动态订阅测试");
        task.setState(taskInit);
        
        task.executeAction(ActionType.START);
        task.executeAction(ActionType.ACHIEVE);
        
        // 取消订阅
        eventBus.unsubscribe(TaskCompletedEvent.EVENT_TYPE, customHandler);
        System.out.println("动态取消注册了自定义事件处理器");
        
        System.out.println("=== 动态订阅演示结束 ===");
    }
    
    /**
     * 演示事件统计
     */
    public void demonstrateEventStatistics() {
        System.out.println("\n=== 事件统计演示 ===");
        
        String eventType = TaskCompletedEvent.EVENT_TYPE;
        int handlerCount = eventBus.getHandlerCount(eventType);
        System.out.printf("事件类型 '%s' 的处理器数量: %d%n", eventType, handlerCount);
        
        System.out.println("=== 事件统计演示结束 ===");
    }
    
    /**
     * 自定义事件处理器
     */
    private static class CustomEventHandler implements com.github.colommar.missionsubexample.event.EventHandler<TaskCompletedEvent> {
        
        @Override
        public void handle(TaskCompletedEvent event) {
            System.out.printf("自定义处理器收到任务完成事件，任务ID: %d%n", event.getTaskId());
        }
        
        @Override
        public String getEventType() {
            return TaskCompletedEvent.EVENT_TYPE;
        }
    }
} 