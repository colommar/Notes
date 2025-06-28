package com.github.colommar.context.strategy;

import com.github.colommar.context.request.WaimaiRequest;
import com.github.colommar.context.service.WaimaiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("WaimaiStrategy")
public class WaimaiStrategy implements Strategy {
    
    @Autowired
    private WaimaiService waimaiService;
    
    @Override
    public void issue(Object... params) {
        WaimaiRequest request = new WaimaiRequest();
        // 构建入参
        request.setWaimaiReq(params);
        waimaiService.issueWaimai(request);
    }
} 