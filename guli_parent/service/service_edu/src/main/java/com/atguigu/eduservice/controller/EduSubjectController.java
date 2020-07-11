package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.subject.OneSubject;
import com.atguigu.eduservice.service.EduSubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author Abner
 * @since 2020-06-29
 */
@RestController
@RequestMapping("/eduservice/subject")
//@CrossOrigin
public class EduSubjectController {

    @Resource
    private EduSubjectService eduSubjectService;

    //添加课程分类
    @PostMapping("/addSubject")
    public R addSubject(MultipartFile file) {
        this.eduSubjectService.saveSubject(file,eduSubjectService);
        return R.ok();
    }

    //课程分类列表
    @GetMapping("/getAllSubject")
    public R getAllSubject() {
        List<OneSubject> list = this.eduSubjectService.getAllOneTwoSubject();
        return R.ok().data("list",list);
    }
}

