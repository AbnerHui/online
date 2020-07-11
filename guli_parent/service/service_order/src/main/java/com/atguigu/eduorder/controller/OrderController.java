package com.atguigu.eduorder.controller;


import com.atguigu.commonutils.JwtUtils;
import com.atguigu.commonutils.R;
import com.atguigu.eduorder.entity.Order;
import com.atguigu.eduorder.service.OrderService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.omg.CORBA.IRObject;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 订单 前端控制器
 * </p>
 *
 * @author Abner
 * @since 2020-07-06
 */
@RestController
@RequestMapping("/eduorder/order")
//@CrossOrigin
public class OrderController {

    @Resource
    private OrderService orderService;

    //生成订单的方法
    @PostMapping("/createOrder/{courseId}")
    public R saveOrder(@PathVariable String courseId, HttpServletRequest request) {
        String orderNo = this.orderService.createOrders(courseId, JwtUtils.getMemberIdByJwtToken(request));
        return R.ok().data("orderId",orderNo);
    }

    //根据订单号查询订单信息
    @GetMapping("/getOrderInfo/{orderId}")
    public R getOrderInfo(@PathVariable String orderId) {
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("order_no",orderId);
        Order order = this.orderService.getOne(wrapper);
        return R.ok().data("item",order);
    }

    //根据课程id用户id查询订单表中订单状态
    @GetMapping("/isBuyCourse/{courseId}/{memberId}")
    public Boolean isBuyCourse(@PathVariable String courseId,@PathVariable String memberId) {
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",courseId);
        wrapper.eq("member_id",memberId);
        wrapper.eq("status",1);
        int count = this.orderService.count(wrapper);
        if(count>0) {
            return true;
        }else{
            return false;
        }
    }
}

