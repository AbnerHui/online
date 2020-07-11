package com.atguigu.eduservice.service.impl;

import com.alibaba.excel.EasyExcel;
import com.atguigu.eduservice.entity.EduSubject;
import com.atguigu.eduservice.entity.excel.SubjectData;
import com.atguigu.eduservice.entity.subject.OneSubject;
import com.atguigu.eduservice.entity.subject.TwoSubject;
import com.atguigu.eduservice.listener.SubjectExcelListener;
import com.atguigu.eduservice.mapper.EduSubjectMapper;
import com.atguigu.eduservice.service.EduSubjectService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author Abner
 * @since 2020-06-29
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {

    @Override
    public void saveSubject(MultipartFile file,EduSubjectService eduSubjectService) {
       try {
           InputStream in = file.getInputStream();
           EasyExcel.read(in, SubjectData.class,new SubjectExcelListener(eduSubjectService)).sheet().doRead();
       }catch (Exception e) {
            e.printStackTrace();
       }
    }

    @Override
    public List<OneSubject> getAllOneTwoSubject() {

        QueryWrapper<EduSubject> wrapperOne = new QueryWrapper<>();
        wrapperOne.eq("parent_id","0");
        List<EduSubject> oneSubjectLists = baseMapper.selectList(wrapperOne);

        QueryWrapper<EduSubject> wrapperTwo = new QueryWrapper<>();
        wrapperTwo.ne("parent_id","0");
        List<EduSubject> twoSubjectLists = baseMapper.selectList(wrapperTwo);

        List<OneSubject> list = new ArrayList<>();
        for (int i = 0; i <oneSubjectLists.size() ; i++) {

            EduSubject eduSubject = oneSubjectLists.get(i);
            OneSubject oneSubject = new OneSubject();

            BeanUtils.copyProperties(eduSubject,oneSubject);
            list.add(oneSubject);

            List<TwoSubject> twoList = new ArrayList<>();
            for (int j = 0; j < twoSubjectLists.size(); j++) {
                EduSubject eduSubject1 = twoSubjectLists.get(j);
                if(eduSubject1.getParentId().equals(eduSubject.getId())) {
                    TwoSubject twoSubject = new TwoSubject();
                    BeanUtils.copyProperties(eduSubject1,twoSubject);
                    twoList.add(twoSubject);
                }
            }

            oneSubject.setChildren(twoList);
        }
        return list;
    }
}
