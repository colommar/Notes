package com.github.colommar.missionsubexample.state;

import com.github.colommar.missionsubexample.enums.ActionType;
import com.github.colommar.missionsubexample.model.Task;

/**
 * 任务过期状态
 */
public class TaskExpired implements State {
    
    @Override
    public void update(Task task, ActionType actionType) {
        // 过期状态是终态，不支持任何动作
        throw new IllegalArgumentException(
            String.format("已过期状态不支持动作: %s", actionType.getMessage()));
    }
} 