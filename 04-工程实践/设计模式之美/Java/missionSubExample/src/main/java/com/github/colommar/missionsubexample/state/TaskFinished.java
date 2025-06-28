package com.github.colommar.missionsubexample.state;

import com.github.colommar.missionsubexample.enums.ActionType;
import com.github.colommar.missionsubexample.model.Task;

/**
 * 任务完成状态
 */
public class TaskFinished implements State {
    
    @Override
    public void update(Task task, ActionType actionType) {
        // 完成状态是终态，不支持任何动作
        throw new IllegalArgumentException(
            String.format("已完成状态不支持动作: %s", actionType.getMessage()));
    }
} 