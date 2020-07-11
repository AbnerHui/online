package com.atguigu.eduservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
@SpringBootApplication
@ComponentScan(basePackages = {"com.atguigu"})
@EnableDiscoveryClient //nacos注册
@EnableFeignClients //在调用端加这个注解
public class EduApplication {

    public static void main(String[] args) {
        SpringApplication.run(EduApplication.class,args);
    }
}
