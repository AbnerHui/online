package com.atguigu.educms;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"com.atguigu"})
@MapperScan("com.atguigu.educms.mapper")
@EnableDiscoveryClient //nacos注册
public class CmsApplication {
    public static void main(String[] args) {
        SpringApplication.run(CmsApplication.class,args);
    }
}
