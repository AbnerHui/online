package com.atguigu.educms.controller;

import com.atguigu.commonutils.R;
import com.atguigu.educms.entity.CrmBanner;
import com.atguigu.educms.service.CrmBannerService;
import jdk.nashorn.internal.ir.LiteralNode;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/educms/bannerfront")
//@CrossOrigin
public class BannerFrontController {

    @Resource
    private CrmBannerService bannerService;

    //查询所有的banner
    @GetMapping("/getAllBanner")
    public R getAllBanner() {
        List<CrmBanner> list = bannerService.selectAllBanner();
        return R.ok().data("list", list);
    }
}
