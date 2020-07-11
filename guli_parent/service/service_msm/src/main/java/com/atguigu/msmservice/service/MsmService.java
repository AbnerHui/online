package com.atguigu.msmservice.service;

import java.util.Map;

public interface MsmService {
    boolean send(Map<String, Object> map, String phone);
}
