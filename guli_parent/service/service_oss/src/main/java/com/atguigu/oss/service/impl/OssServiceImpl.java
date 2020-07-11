package com.atguigu.oss.service.impl;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.atguigu.oss.service.OssService;
import com.atguigu.oss.utils.ConstantPropertiesUtils;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.InputStream;
import java.util.UUID;

@Service
public class OssServiceImpl implements OssService {

    @Override
    public String uploadFileAvatar(MultipartFile file) {

        OSS ossClient = null;
        InputStream inputStream = null;
        try {
            // 创建OSSClient实例。
            ossClient  = new OSSClientBuilder().
                    build(ConstantPropertiesUtils.END_POINT,
                            ConstantPropertiesUtils.ACCESS_KEY_ID,
                            ConstantPropertiesUtils.ACCESS_KEY_SECRET);
            // 上传文件流。
            inputStream = file.getInputStream();

            //生成唯一的文件名
            String fileName = file.getOriginalFilename();
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");
            fileName = uuid + fileName;

            //获取当前日期
            String date = new DateTime().toString("yyyy/MM/dd");
            fileName = date + "/" + fileName;

            //第一个参数Bucket名称 第二个参数 上传到oss文件路径和文件名称
            ossClient.putObject(ConstantPropertiesUtils.BUCKET_NAME,fileName, inputStream);

            // 关闭OSSClient。
            ossClient.shutdown();

            String url = "https://"+ConstantPropertiesUtils.BUCKET_NAME+"."+ ConstantPropertiesUtils.END_POINT+"/"+fileName;

            return url;
       } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
