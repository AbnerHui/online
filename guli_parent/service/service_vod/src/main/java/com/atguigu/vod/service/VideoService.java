package com.atguigu.vod.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface VideoService {
    String uploadAlyVideo(MultipartFile file);

    void removeMoreAlyVideo(List videoIdList);
}
