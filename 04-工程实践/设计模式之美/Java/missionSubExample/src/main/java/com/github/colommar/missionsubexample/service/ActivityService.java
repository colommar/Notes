package com.github.colommar.missionsubexample.service;

import org.springframework.stereotype.Service;

/**
 * 活动服务
 */
@Service
public class ActivityService {
    
    /**
     * 通知任务完成
     */
    public void notifyFinished(Long taskId) {
        System.out.println("活动服务收到任务完成通知，任务ID: " + taskId);
        // 这里可以添加具体的业务逻辑
        // 比如更新活动进度、发放奖励等
    }
    
    /**
     * 通知任务暂停
     */
    public void notifyPaused(Long taskId) {
        System.out.println("活动服务收到任务暂停通知，任务ID: " + taskId);
    }
    
    /**
     * 通知任务过期
     */
    public void notifyExpired(Long taskId) {
        System.out.println("活动服务收到任务过期通知，任务ID: " + taskId);
    }
} 