package com.atguigu.eduorder.service;

import com.atguigu.eduorder.entity.Order;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 订单 服务类
 * </p>
 *
 * @author Abner
 * @since 2020-07-06
 */
public interface OrderService extends IService<Order> {

    String createOrders(String courseId, String memberIdByJwtToken);
}
