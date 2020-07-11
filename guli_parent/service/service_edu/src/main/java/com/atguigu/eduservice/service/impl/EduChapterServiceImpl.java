package com.atguigu.eduservice.service.impl;

import com.atguigu.eduservice.entity.EduChapter;
import com.atguigu.eduservice.entity.EduVideo;
import com.atguigu.eduservice.entity.chapter.ChapterVo;
import com.atguigu.eduservice.entity.chapter.VideoVo;
import com.atguigu.eduservice.mapper.EduChapterMapper;
import com.atguigu.eduservice.service.EduChapterService;
import com.atguigu.eduservice.service.EduVideoService;
import com.atguigu.servicebase.exceptionhandler.GuliException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author Abner
 * @since 2020-06-30
 */
@Service
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {

    @Resource
    private EduVideoService videoService;

    @Override
    public List<ChapterVo> getChapterVideoByCourseId(String courseId) {
        QueryWrapper<EduChapter> wrapperChapter = new QueryWrapper<>();
        wrapperChapter.eq("course_id",courseId);
        List<EduChapter> eduChapterList = baseMapper.selectList(wrapperChapter);

        QueryWrapper<EduVideo> wrapperVideo = new QueryWrapper<>();
        wrapperVideo.eq("course_id",courseId);
        List<EduVideo> eduVideoList = this.videoService.list(wrapperVideo);

        List<ChapterVo> list = new ArrayList<>();
        for (int i = 0; i < eduChapterList.size(); i++) {
            
            EduChapter eduChapter = eduChapterList.get(i);
            ChapterVo chapterVo = new ChapterVo();
            BeanUtils.copyProperties(eduChapter,chapterVo);
            list.add(chapterVo);

            List<VideoVo> videoVoList = new ArrayList<>();
            for (int j = 0; j < eduVideoList.size(); j++) {
                EduVideo eduVideo = eduVideoList.get(j);
                if(eduVideo.getChapterId().equals(eduChapter.getId())) {
                    VideoVo videoVo = new VideoVo();
                    BeanUtils.copyProperties(eduVideo,videoVo);
                    videoVoList.add(videoVo);
                }
            }
            chapterVo.setChildren(videoVoList);
        }
        return list;
    }

    @Override
    public boolean deleteChapter(String chapterId) {
        QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();
        wrapper.eq("chapter_id",chapterId);
        int count = this.videoService.count(wrapper);
        if(count>0) {
            throw new GuliException(20001,"不能删除");
        }else {
            int res = baseMapper.deleteById(chapterId);
            return res>0;
        }
    }

    @Override
    public void removeChapterCourseId(String courseId) {
        QueryWrapper<EduChapter> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",courseId);
        baseMapper.delete(wrapper);
}
}
