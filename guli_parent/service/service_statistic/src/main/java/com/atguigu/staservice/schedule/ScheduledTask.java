package com.atguigu.staservice.schedule;

import com.atguigu.staservice.service.StatisticsDailyService;
import com.atguigu.staservice.utils.DateUtil;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;

@Component
public class ScheduledTask {

    @Resource
    private StatisticsDailyService dailyService;

    //每天凌晨一点执行此方法,把数据查询添加
    @Scheduled(cron = "0 0 1 * * ? ")
    public void task2() {
        //把前一天的数据添加到数据库
        this.dailyService.registerCount(DateUtil.formatDate(DateUtil.addDays(new Date(),-1)));
    }

    /**
     * 测试
     * 每五秒执行一次
     */
//    @Scheduled(cron = "0/5 * * * * ?") //springboot整合默认是6位,年省略默认是当前年
//    public void task1() {
//        System.out.println("*********++++++++++++*****执行了");
//    }
}
