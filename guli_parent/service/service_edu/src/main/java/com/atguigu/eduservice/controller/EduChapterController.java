package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.EduChapter;
import com.atguigu.eduservice.entity.chapter.ChapterVo;
import com.atguigu.eduservice.service.EduChapterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author Abner
 * @since 2020-06-30
 */
@RestController
@RequestMapping("/eduservice/chapter")
//@CrossOrigin
public class EduChapterController {

    @Autowired
    private EduChapterService chapterService;

    //课程大纲列表
    @GetMapping("/getChapterVideo/{courseId}")
    public R getChapterVideo(@PathVariable String courseId) {
        List<ChapterVo> list = this.chapterService.getChapterVideoByCourseId(courseId);
        return R.ok().data("allChapterVideo",list);
    }

    //添加章节
    @PostMapping("/addChapter")
    public R addChapter(@RequestBody EduChapter eduChapter) {
        this.chapterService.save(eduChapter);
        return R.ok();
    }

    //根据章节id进行查询
    @GetMapping("/getChapterInfo/{chapterId}")
    public R getChapterInfo(@PathVariable String chapterId) {
        EduChapter eduChapter = this.chapterService.getById(chapterId);
        return R.ok().data("chapter",eduChapter);
    }

    //修改章节
    @PostMapping("/updateChapter")
    public R updateChapter(@RequestBody EduChapter eduChapter) {
         this.chapterService.updateById(eduChapter);
         return R.ok();
    }

    //删除章节
    @DeleteMapping("{chapterId}")
    public R deleteChapter(@PathVariable String chapterId) {
        boolean flag = this.chapterService.deleteChapter(chapterId);
        if(flag){
            return R.ok();
        }else {
            return R.error();
        }

    }



}

