package com.github.colommar.missionsubexample.state;

import com.github.colommar.missionsubexample.enums.ActionType;
import com.github.colommar.missionsubexample.model.Task;

/**
 * 任务暂停状态
 */
public class TaskPaused implements State {
    
    @Override
    public void update(Task task, ActionType actionType) {
        switch (actionType) {
            case START:
                task.setState(new TaskOngoingV2());
                System.out.printf("任务 %d: 暂停中 --开始--> 进行中%n", task.getTaskId());
                break;
                
            case EXPIRE:
                task.setState(new TaskExpired());
                System.out.printf("任务 %d: 暂停中 --过期--> 已过期%n", task.getTaskId());
                break;
                
            default:
                throw new IllegalArgumentException(
                    String.format("暂停中状态不支持动作: %s", actionType.getMessage()));
        }
    }
} 