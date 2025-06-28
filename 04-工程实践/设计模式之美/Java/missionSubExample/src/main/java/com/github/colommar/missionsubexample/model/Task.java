package com.github.colommar.missionsubexample.model;

import com.github.colommar.missionsubexample.state.State;

/**
 * 任务实体
 */
public class Task {
    
    /**
     * 任务ID
     */
    private Long taskId;
    
    /**
     * 任务名称
     */
    private String taskName;
    
    /**
     * 任务描述
     */
    private String description;
    
    /**
     * 任务状态
     */
    private State state;
    
    public Task() {}
    
    public Task(Long taskId, String taskName, String description, State state) {
        this.taskId = taskId;
        this.taskName = taskName;
        this.description = description;
        this.state = state;
    }
    
    /**
     * 执行动作
     * @param actionType 动作类型
     */
    public void executeAction(com.github.colommar.missionsubexample.enums.ActionType actionType) {
        if (state != null) {
            state.update(this, actionType);
        } else {
            throw new IllegalStateException("任务状态未初始化");
        }
    }
    
    /**
     * 获取当前状态名称
     */
    public String getCurrentStateName() {
        if (state == null) {
            return "未初始化";
        }
        return state.getClass().getSimpleName();
    }
    
    // Getters and Setters
    public Long getTaskId() {
        return taskId;
    }
    
    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }
    
    public String getTaskName() {
        return taskName;
    }
    
    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public State getState() {
        return state;
    }
    
    public void setState(State state) {
        this.state = state;
    }
} 