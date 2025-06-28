package com.github.colommar.context.context;

import com.github.colommar.context.strategy.Strategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.Map;

@Component
public class StrategyContext {
    
    @Autowired
    private Map<String, Strategy> strategyMap;
    
    public Strategy getStrategy(String rewardType) {
        Strategy strategy = strategyMap.get(rewardType + "Strategy");
        if (strategy == null) {
            throw new IllegalArgumentException("Unknown strategy: " + rewardType);
        }
        return strategy;
    }
}
