package com.atguigu.eduservice.client;

import com.atguigu.commonutils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "service-vod", fallback = VodFileDegradeFeignClient.class)
//要调用得服务端名字
@Component
public interface VodClient {

    //定义调用得方法路径
    @DeleteMapping("/eduvod/video/removeAliyunVideo/{id}")
    public R removeAliyunVideo(@PathVariable("id") String id);

    //删除多个阿里云视频得方法
    @DeleteMapping("/eduvod/video/delete-batch")
    public R deleteBatch(@RequestParam("videoIdList") List<String> videoIdList);
}
