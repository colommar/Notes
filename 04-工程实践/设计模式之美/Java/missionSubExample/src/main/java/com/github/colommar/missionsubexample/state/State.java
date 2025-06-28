package com.github.colommar.missionsubexample.state;

import com.github.colommar.missionsubexample.enums.ActionType;
import com.github.colommar.missionsubexample.model.Task;

/**
 * 状态接口
 */
public interface State {
    /**
     * 更新任务状态
     * @param task 任务对象
     * @param actionType 动作类型
     */
    void update(Task task, ActionType actionType);
} 