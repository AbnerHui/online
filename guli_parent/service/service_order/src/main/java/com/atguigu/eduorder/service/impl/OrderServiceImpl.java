package com.atguigu.eduorder.service.impl;

import com.atguigu.commonutils.CourseWebVoOrder;
import com.atguigu.commonutils.UcenterMemberOrder;
import com.atguigu.eduorder.client.EduClient;
import com.atguigu.eduorder.client.UcenterClient;
import com.atguigu.eduorder.entity.Order;
import com.atguigu.eduorder.mapper.OrderMapper;
import com.atguigu.eduorder.service.OrderService;
import com.atguigu.eduorder.utils.OrderNoUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * 订单 服务实现类
 * </p>
 *
 * @author Abner
 * @since 2020-07-06
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    @Resource
    private EduClient eduClient;

    @Resource
    private UcenterClient ucenterClient;

    @Override

    public String createOrders(String courseId, String memberId) {
        //通过远程调用获取用户信息
        UcenterMemberOrder userInfoOrder = this.ucenterClient.getUserInfoOrder(memberId);
        //通过远程调用获取课程信息
        CourseWebVoOrder courseInfoOrder = this.eduClient.getCourseInfoOrder(courseId);

        Order order = new Order();
        order.setOrderNo(OrderNoUtil.getOrderNo()); //订单号
        order.setCourseId(courseId); //课程id
        order.setCourseTitle(courseInfoOrder.getTitle());
        order.setCourseCover(courseInfoOrder.getCover());
        order.setTeacherName(courseInfoOrder.getTeacherName());
        order.setTotalFee(courseInfoOrder.getPrice());
        order.setMemberId(memberId);
        order.setMobile(userInfoOrder.getMobile());
        order.setNickname(userInfoOrder.getNickname());
        order.setStatus(0);  //0未支付 1已支付
        order.setPayType(1); //支付类型 微信支付1
        baseMapper.insert(order);
        return order.getOrderNo();
    }
}
