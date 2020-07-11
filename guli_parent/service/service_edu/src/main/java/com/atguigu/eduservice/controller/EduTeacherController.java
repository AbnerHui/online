package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.EduTeacher;
import com.atguigu.eduservice.entity.vo.TeacherQuery;
import com.atguigu.eduservice.service.EduTeacherService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author Abner
 * @since 2020-06-24
 */
@RestController
@RequestMapping("/eduservice/teacher")
@Api(description = "讲师管理")
//@CrossOrigin //解决跨域
public class EduTeacherController {

    @Autowired
    private EduTeacherService eduTeacherService;

    //查询讲师所有数据
    @GetMapping("/findAll")
    @ApiOperation(value = "所有讲师列表")
    public R findAll() {
        List<EduTeacher> list = this.eduTeacherService.list(null);
        return R.ok().data("items",list);
    }

    //逻辑删除
    @DeleteMapping("{id}")
    @ApiOperation(value = "逻辑删除讲师")
    public R removeTeacher(@ApiParam(name = "id", value = "讲师ID", required = true) @PathVariable String id){
        boolean flag = this.eduTeacherService.removeById(id);
        if(flag){
            return R.ok();
        }else{
            return R.error();
        }
    }

    //分页查询讲师的方法 current当前页  limit每页记录数
    @GetMapping("/pageTeacher/{current}/{limit}")
    public R pageListTeacher(@PathVariable long current,@PathVariable long limit){
        Page<EduTeacher> page = new Page<>(current,limit);
        this.eduTeacherService.page(page,null);
        long total = page.getTotal(); //总记录数
        List<EduTeacher> records = page.getRecords();  //list集合

//        try {
//            int a = 10/0;
//        }catch (Exception e){
//            throw new GuliException(20001,"执行的自定义异常处理");
//        }

        Map map = new HashMap();
        map.put("total",total);
        map.put("rows",records);

        return R.ok().data(map);
    }

    //条件查询带分页的方法
    //@RequestBody(required = false) 这个值可以没有的意思
    @PostMapping("pageTeacherCondition/{current}/{limit}")
    public R pageTeacherCondition(@PathVariable long current, @PathVariable long limit,
                                  @RequestBody(required = false) TeacherQuery teacherQuery){

        Page<EduTeacher> page = new Page<>(current,limit);
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper();

        if(!StringUtils.isEmpty(teacherQuery.getName())) {
           wrapper.like("name",teacherQuery.getName());
        }

        if(!StringUtils.isEmpty(teacherQuery.getLevel())) {
            wrapper.eq("level",teacherQuery.getLevel());
        }

        if(!StringUtils.isEmpty(teacherQuery.getBegin())) {
            wrapper.ge("gmt_create",teacherQuery.getBegin());
        }

        if(!StringUtils.isEmpty(teacherQuery.getEnd())) {
            wrapper.le("gmt_modified",teacherQuery.getEnd());
        }

        wrapper.orderByDesc("gmt_modified");
        this.eduTeacherService.page(page,wrapper);
        long total = page.getTotal(); //总记录数
        List<EduTeacher> records = page.getRecords();  //list集合

        return R.ok().data("total",total).data("rows",records);
    }

    //添加讲师
    @PostMapping("/addTeacher")
    public R addTeacher(@RequestBody EduTeacher eduTeacher){
        boolean save = eduTeacherService.save(eduTeacher);
        if(save){
            return R.ok();
        }else{
            return R.error();
        }
    }

    //根据讲师ID进行查询
    @GetMapping("/getTeacher/{id}")
    public R getTeacher(@PathVariable String id){
        EduTeacher teacher = this.eduTeacherService.getById(id);
        return R.ok().data("teacher",teacher);
    }

    //修改讲师
    @PostMapping("/updateTeacher")
    public R updateTeacher(@RequestBody EduTeacher eduTeacher){
        boolean b = this.eduTeacherService.updateById(eduTeacher);
        if(b){
            return R.ok();
        }else {
            return R.error();
        }
    }
}

