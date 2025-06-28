package com.github.colommar.context.service;

import com.github.colommar.context.strategy.Strategy;
import com.github.colommar.context.context.StrategyContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RewardService {
    
    @Autowired
    private StrategyContext strategyContext;
    
    public void issueReward(String type, Object... params) {
        Strategy strategy = strategyContext.getStrategy(type);
        strategy.issue(params);
    }
    
    // 使用示例
    public void example() {
        // 发放外卖奖励
        issueReward("Waimai", "user123", "coupon001");
        
        // 发放酒店奖励
        issueReward("Hotel", "user456", "hotel001");
        
        // 发放美食奖励
        issueReward("Food", "user789", "food001");
    }
} 