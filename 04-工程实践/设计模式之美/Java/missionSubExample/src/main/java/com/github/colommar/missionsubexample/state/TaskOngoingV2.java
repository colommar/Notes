package com.github.colommar.missionsubexample.state;

import com.github.colommar.missionsubexample.enums.ActionType;
import com.github.colommar.missionsubexample.event.EventBus;
import com.github.colommar.missionsubexample.event.TaskCompletedEvent;
import com.github.colommar.missionsubexample.event.TaskExpiredEvent;
import com.github.colommar.missionsubexample.event.TaskPausedEvent;
import com.github.colommar.missionsubexample.model.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 任务进行状态 - 使用事件总线版本
 */
@Component
public class TaskOngoingV2 implements State {
    
    @Autowired
    private EventBus eventBus;
    
    @Override
    public void update(Task task, ActionType actionType) {
        switch (actionType) {
            case ACHIEVE:
                task.setState(new TaskFinished());
                // 发布任务完成事件
                TaskCompletedEvent completedEvent = new TaskCompletedEvent(task.getTaskId(), task);
                eventBus.publish(completedEvent);
                System.out.printf("任务 %d: 进行中 --完成--> 已完成%n", task.getTaskId());
                break;
                
            case STOP:
                task.setState(new TaskPaused());
                // 发布任务暂停事件
                TaskPausedEvent pausedEvent = new TaskPausedEvent(task.getTaskId(), task);
                eventBus.publish(pausedEvent);
                System.out.printf("任务 %d: 进行中 --暂停--> 暂停中%n", task.getTaskId());
                break;
                
            case EXPIRE:
                task.setState(new TaskExpired());
                // 发布任务过期事件
                TaskExpiredEvent expiredEvent = new TaskExpiredEvent(task.getTaskId(), task);
                eventBus.publish(expiredEvent);
                System.out.printf("任务 %d: 进行中 --过期--> 已过期%n", task.getTaskId());
                break;
                
            default:
                throw new IllegalArgumentException(
                    String.format("进行中状态不支持动作: %s", actionType.getMessage()));
        }
    }
} 