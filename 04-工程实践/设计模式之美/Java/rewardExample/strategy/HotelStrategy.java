package com.github.colommar.context.strategy;

import com.github.colommar.context.request.HotelRequest;
import com.github.colommar.context.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("HotelStrategy")
public class HotelStrategy implements Strategy {
    
    @Autowired
    private HotelService hotelService;
    
    @Override
    public void issue(Object... params) {
        HotelRequest request = new HotelRequest();
        request.addHotelReq(params);
        hotelService.sendPrize(request);
    }
} 