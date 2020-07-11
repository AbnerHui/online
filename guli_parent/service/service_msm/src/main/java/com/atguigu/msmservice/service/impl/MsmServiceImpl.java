package com.atguigu.msmservice.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.nacos.client.naming.utils.StringUtils;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.atguigu.msmservice.service.MsmService;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class MsmServiceImpl implements MsmService {
    @Override
    public boolean send(Map<String, Object> map, String phone) {
        if(StringUtils.isEmpty(phone)) return false;

        DefaultProfile profile =
        DefaultProfile.getProfile("default", "LTAI4FzLNxBrS2Rxc563CFH9", "hmRh0ttFkAITrjPXUD5c4mv9vLNKNC");
        IAcsClient client = new DefaultAcsClient(profile);

        //设置相关固定的相关参数
        CommonRequest request = new CommonRequest();
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");

        //设置发送相关的参数
        request.putQueryParameter("PhoneNumbers",phone); //手机号
        request.putQueryParameter("SignName", "Abner在线教育网站"); //签名名称
        request.putQueryParameter("TemplateCode", "SMS_195221031"); //模板code
        request.putQueryParameter("TemplateParam", JSONObject.toJSONString(map)); //验证数据必须是json数据

        //最终发送
        try {
            CommonResponse response = client.getCommonResponse(request);
            boolean success = response.getHttpResponse().isSuccess();
            return success;
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
