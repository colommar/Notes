package com.github.colommar.missionsubexample.state;

import com.github.colommar.missionsubexample.enums.ActionType;
import com.github.colommar.missionsubexample.model.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 任务初始状态
 */
@Component
public class TaskInit implements State {
    
    @Autowired
    private TaskOngoingV2 taskOngoingV2;
    
    @Override
    public void update(Task task, ActionType actionType) {
        if (actionType == ActionType.START) {
            task.setState(taskOngoingV2);
            System.out.printf("任务 %d: 初始化 --开始--> 进行中%n", task.getTaskId());
        } else {
            throw new IllegalArgumentException(
                String.format("初始状态不支持动作: %s", actionType.getMessage()));
        }
    }
} 