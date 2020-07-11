package com.atguigu.eduservice.controller.front;

import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.EduTeacher;
import com.atguigu.eduservice.service.EduCourseService;
import com.atguigu.eduservice.service.EduTeacherService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/eduservice/indexfront")
//@CrossOrigin
public class IndexFrontController {

    @Resource
    private EduCourseService courseService;

    @Resource
    private EduTeacherService teacherService;

    //查询8条热门课程,查询前4条名师
    @GetMapping("/index")
    //@Cacheable(key = "'index'",value = "edu")
    public R index() {
        //查询8条热门课程
        QueryWrapper<EduCourse> courWrapper = new QueryWrapper<>();
        courWrapper.orderByDesc("id");
        courWrapper.last("limit 8");
        List<EduCourse> eduList = this.courseService.list(courWrapper);

        //查询前4条名师
        QueryWrapper<EduTeacher> teaWrapper = new QueryWrapper<>();
        teaWrapper.orderByDesc("id");
        teaWrapper.last("limit 4");
        List<EduTeacher> teacherList = this.teacherService.list(teaWrapper);
        return R.ok().data("eduList",eduList).data("teacherList",teacherList);
    }
}
