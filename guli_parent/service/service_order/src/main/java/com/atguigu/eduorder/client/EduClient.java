package com.atguigu.eduorder.client;

import com.atguigu.commonutils.CourseWebVoOrder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Component
@FeignClient("service-edu")
public interface EduClient {
    //根据课程id查询课程信息
    @PostMapping("/eduservice/coursefont/getCourseInfoOrder/{id}")
    public CourseWebVoOrder getCourseInfoOrder(@PathVariable("id") String id);
}
