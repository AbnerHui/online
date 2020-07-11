package com.atguigu.oss.controller;

import com.atguigu.commonutils.R;
import com.atguigu.oss.service.OssService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

@RestController
@RequestMapping("/eduoss/fileOss")
//@CrossOrigin
public class OssController {

    @Resource
    private OssService ossService;

    //上传头像的方法
    @PostMapping
    public R UploadOssFile(MultipartFile file) {
        String url = this.ossService.uploadFileAvatar(file);
        return R.ok().data("url",url);
    }
}
