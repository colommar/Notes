package com.github.colommar.context.strategy;

import com.github.colommar.context.request.FoodRequest;
import com.github.colommar.context.service.FoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("FoodStrategy")
public class FoodStrategy implements Strategy {
    
    @Autowired
    private FoodService foodService;
    
    @Override
    public void issue(Object... params) {
        FoodRequest request = new FoodRequest(params);
        foodService.payCoupon(request);
    }
} 