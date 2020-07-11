package com.atguigu.staservice.service.impl;

import com.atguigu.commonutils.R;
import com.atguigu.staservice.client.UcenterClient;
import com.atguigu.staservice.entity.StatisticsDaily;
import com.atguigu.staservice.mapper.StatisticsDailyMapper;
import com.atguigu.staservice.service.StatisticsDailyService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务实现类
 * </p>
 *
 * @author Abner
 * @since 2020-07-07
 */
@Service
public class StatisticsDailyServiceImpl extends ServiceImpl<StatisticsDailyMapper, StatisticsDaily> implements StatisticsDailyService {

    @Resource
    private UcenterClient client;

    @Override
    public void registerCount(String day) {

        //添加记录之前先删除
        QueryWrapper<StatisticsDaily> wrapper = new QueryWrapper<>();
        wrapper.eq("date_calculated",day);
        baseMapper.delete(wrapper);

        //远程调用得到某一天注册人数
        R r = this.client.countRegister(day);
        Integer countRegister = (Integer) r.getData().get("countRegister");
        //把获取到的数据添加数据库
        StatisticsDaily sta = new StatisticsDaily();
        sta.setRegisterNum(countRegister); //注册人数
        sta.setDateCalculated(day); //统计日期
        //用随机数模拟下
        sta.setVideoViewNum(RandomUtils.nextInt(100,200));
        sta.setLoginNum(RandomUtils.nextInt(100,200));
        sta.setCourseNum(RandomUtils.nextInt(100,200));
        baseMapper.insert(sta);
    }

    @Override
    public Map<String, Object> getShowData(String type, String begin, String end) {
        QueryWrapper<StatisticsDaily> wrapper = new QueryWrapper<>();
        wrapper.between("date_calculated",begin,end);
        wrapper.select("date_calculated",type);
        List<StatisticsDaily> staList = baseMapper.selectList(wrapper);

        List<String> date_calculatedList = new ArrayList<>();
        List<Integer> numDataList = new ArrayList<>();

        for (int i = 0; i < staList.size(); i++ ) {
            StatisticsDaily daily = staList.get(i);
            //封装日期集合
            date_calculatedList.add(daily.getDateCalculated());
            //封装对应数量
            switch (type) {
                case "login_num":
                    numDataList.add(daily.getLoginNum());
                    break;
                case "register_num":
                    numDataList.add(daily.getRegisterNum());
                    break;
                case "video_view_num":
                    numDataList.add(daily.getVideoViewNum());
                    break;
                case "course_num":
                    numDataList.add(daily.getCourseNum());
                    break;
                default:
                    break;
            }
        }
        Map<String,Object> map = new HashMap<>();
        map.put("date_calculatedList",date_calculatedList);
        map.put("numDataList",numDataList);
        return map;
    }
}
