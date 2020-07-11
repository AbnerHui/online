package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.R;
import com.atguigu.eduservice.client.VodClient;
import com.atguigu.eduservice.entity.EduVideo;
import com.atguigu.eduservice.service.EduVideoService;
import com.atguigu.servicebase.exceptionhandler.GuliException;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author Abner
 * @since 2020-06-30
 */
@RestController
@RequestMapping("/eduservice/video")
//@CrossOrigin
public class EduVideoController {

    @Resource
    private EduVideoService eduVideoService;

    @Resource
    private VodClient vodClient;

    //添加小节
    @PostMapping("/addVideo")
    public R addVideo(@RequestBody EduVideo eduVideo) {
        this.eduVideoService.save(eduVideo);
        return R.ok();
    }

    //删除小节
    @DeleteMapping("{id}")
    public R deleteVideo(@PathVariable String id) {
        EduVideo eduVideo = this.eduVideoService.getById(id);
        String videoSourceId = eduVideo.getVideoSourceId();
        if(!StringUtils.isEmpty(videoSourceId)){
            R r = this.vodClient.removeAliyunVideo(videoSourceId);
            if(r.getCode() == 20001) {
                throw new GuliException(20001,"删除视频失败,熔断器");
            }
        }
        this.eduVideoService.removeById(id);
        return R.ok();
    }

    //根据小节id查询
    @GetMapping("/getVideoInfo/{id}")
    public R getVideoInfo(@PathVariable String id) {
        EduVideo eduVideo = this.eduVideoService.getById(id);
        return R.ok().data("eduVideo",eduVideo);
    }

    //修改小节
    @PostMapping("/updateVideo")
    public R updateVideo(@RequestBody EduVideo eduVideo) {
        this.eduVideoService.updateById(eduVideo);
        return R.ok();
    }

}

