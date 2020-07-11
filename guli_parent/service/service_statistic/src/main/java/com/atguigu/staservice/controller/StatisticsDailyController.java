package com.atguigu.staservice.controller;


import com.atguigu.commonutils.R;
import com.atguigu.staservice.service.StatisticsDailyService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

/**
 * <p>
 * 网站统计日数据 前端控制器
 * </p>
 *
 * @author Abner
 * @since 2020-07-07
 */
@RestController
@RequestMapping("/staservice/sta")
//@CrossOrigin
public class StatisticsDailyController {

    @Resource
    private StatisticsDailyService dailyService;

    //统计某一天注册人数,生成统计数据
    @PostMapping("/registerCount/{day}")
    public R registerCount(@PathVariable String day) {
        this.dailyService.registerCount(day);
        return R.ok();
    }

    //图标显示,返回日期json数组,数量json数组
    @GetMapping("/showData/{type}/{begin}/{end}")
    public R showData(@PathVariable String type,@PathVariable String begin,@PathVariable String end) {
        Map<String,Object> map = this.dailyService.getShowData(type,begin,end);
        return R.ok().data(map);
    }
}

