package com.atguigu.eduorder.controller;


import com.atguigu.commonutils.R;
import com.atguigu.eduorder.service.PayLogService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

/**
 * <p>
 * 支付日志表 前端控制器
 * </p>
 *
 * @author Abner
 * @since 2020-07-06
 */
@RestController
@RequestMapping("/eduorder/paylog")
//@CrossOrigin
public class PayLogController {

    @Resource
    private PayLogService payLogService;

    //生成微信支付二维码
    @GetMapping("/createNative/{orderNo}")
    public R createNative(@PathVariable String orderNo) {
        //返回相关信息(二维码地址,还有其他信息)
        Map map = this.payLogService.createNative(orderNo);
        return R.ok().data(map);
    }

    //查询订单支付状态
    @GetMapping("/queryPayStatus/{orderNo}")
    public R queryPayStatus(@PathVariable String orderNo) {
        Map<String,String> map = this.payLogService.queryPayStatus(orderNo);
        if(map == null) {
            return R.error().message("支付失败");
        }
        if(map.get("trade_state").equals("SUCCESS"))  {
            //添加支付记录,并更新订单表
            this.payLogService.updateOrderStatus(map);
            return R.ok().message("支付成功");
        }
        return R.ok().code(25000).message("支付中");
    }
}

