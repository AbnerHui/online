package com.atguigu.eduservice.client;

import org.springframework.stereotype.Component;

@Component
public class OrderFileDegradeFeignClient implements OrdersClient {
    @Override
    public Boolean isBuyCourse(String courseId, String memberId) {
        return false;
    }
}
