package com.atguigu.msmservice.controller;

import com.atguigu.commonutils.R;
import com.atguigu.msmservice.service.MsmService;
import com.atguigu.msmservice.utils.RandomUtil;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/edumsm/msm")
//@CrossOrigin //解决跨域
public class MsmController {

    @Resource
    private MsmService msmService;

    @Resource
    private RedisTemplate<String,String> redisTemplate;

    //根据手机号发送短信
    @GetMapping("/send/{phone}")
    public R sendMsm(@PathVariable String phone) {
        //从redis获取验证码,如果获取到返回
        String code = redisTemplate.opsForValue().get(phone);
        if(!StringUtils.isEmpty(code)) {
            return R.ok();
        }
        //获取不到,像阿里云发送
        //生成随机验证码,传送给阿里云,阿里云发短信
        code = RandomUtil.getFourBitRandom();
        Map<String,Object> map = new HashMap<>();
        map.put("code",code);
        //调用service发送短信的方法
        boolean isSend = this.msmService.send(map,phone);
        if(isSend){
            //发送成功,把发送成功验证码放到redis里面并设置有效时间
            redisTemplate.opsForValue().setIfAbsent(phone,code,1,TimeUnit.MINUTES);
            return R.ok();
        }else {
            return R.error().message("短信传送失败");
        }

    }

}
