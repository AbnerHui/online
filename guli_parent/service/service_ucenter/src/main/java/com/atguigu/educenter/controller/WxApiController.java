package com.atguigu.educenter.controller;

import com.atguigu.commonutils.JwtUtils;
import com.atguigu.educenter.entity.UcenterMember;
import com.atguigu.educenter.service.UcenterMemberService;
import com.atguigu.educenter.utils.ConstantPropertiesUtil;
import com.atguigu.educenter.utils.HttpClientUtils;
import com.atguigu.servicebase.exceptionhandler.GuliException;
import com.google.gson.Gson;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.lang.reflect.Member;
import java.net.URLEncoder;
import java.util.HashMap;

//@CrossOrigin
@Controller
@RequestMapping("/api/ucenter/wx")//http://localhost:8150/api/ucenter/wx/callback
public class WxApiController {

    @Resource
    private UcenterMemberService memberService;

    //生成微信二维码
    @GetMapping("/login")
    public String getWxCode() {

        //1.微信开放平台授权baseUrl %s相当于占位符
        String baseUrl = "https://open.weixin.qq.com/connect/qrconnect" +
                        "?appid=%s" +
                        "&redirect_uri=%s" +
                        "&response_type=code" +
                        "&scope=snsapi_login" +
                        "&state=%s" +
                        "#wechat_redirect";
        //需要对redirect_uri进行urlEncode编码
        String redirectUrl = ConstantPropertiesUtil.WX_OPEN_REDIRECT_URL;
        try {
            redirectUrl = URLEncoder.encode(redirectUrl,"utf-8");
        }catch (Exception e) {
            e.printStackTrace();
        }
        //往%s里面赋值
        String url = String.format(baseUrl, ConstantPropertiesUtil.WX_OPEN_APP_ID,redirectUrl,"atguigu");
        //重定向到请求微信地址里面
        return "redirect:"+url;
    }

    //2.获取扫描人信息,添加数据
    @GetMapping("callback")
    public String callback(String code,String state) {
        try {
            //1.获取code值,临时票据
            //2.用code去请求微信提供的借口
            String baseAccessTokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token" +
                    "?appid=%s" +
                    "&secret=%s" +
                    "&code=%s" +
                    "&grant_type=authorization_code";
            //将值拼接
            String accessTokenUrl = String.format(
                    baseAccessTokenUrl,ConstantPropertiesUtil.WX_OPEN_APP_ID,
                    ConstantPropertiesUtil.WX_OPEN_APP_SECRET,code );
            //用这个拼接好的地址去请求 得到access_token和openid
            //使用httpClient去请求
            String accessTokenInfo = HttpClientUtils.get(accessTokenUrl);
            //将字符串accessTokenInfo转换为map(gson) 并取出想到的值
            Gson gson = new Gson();
            HashMap map = gson.fromJson(accessTokenInfo, HashMap.class);
            String access_token = (String) map.get("access_token");
            String openid = (String) map.get("openid");

            //把扫码人信息加入到数据库就不用注册了
            //如果该用户已经注册过就不注册
            UcenterMember member = this.memberService.getOpenIdMember(openid);
            if(member == null) {
                //如果未注册就去请求,否则直接登陆
                //3.拿着access_token和openid再去请求微信的一个接口,获取扫描人信息
                String baseUserInfoUrl = "https://api.weixin.qq.com/sns/userinfo" +
                        "?access_token=%s" +
                        "&openid=%s";
                //拼接参数
                String userInfoUrl = String.format(baseUserInfoUrl, access_token, openid);
                //发送请求
                String userInfo = HttpClientUtils.get(userInfoUrl);
                //获取扫码人的信息
                HashMap userInfoMap = gson.fromJson(userInfo, HashMap.class);
                String nickname = (String) userInfoMap.get("nickname"); //昵称
                String headimgurl = (String) userInfoMap.get("headimgurl");//头像

                member = new UcenterMember();
                member.setOpenid(openid);
                member.setNickname(nickname);
                member.setAvatar(headimgurl);
                this.memberService.save(member);
            }

            //使用jwt根据member对象生成token字符串
            String jwtToken = JwtUtils.getJwtToken(member.getId(), member.getNickname());
            //最后返回到主页 通过路径传递token 为了解决不同域名跨域问题(cookie会有这个问题)
            return "redirect:http://localhost:3000?token="+jwtToken;
        } catch (Exception e) {
           throw new GuliException(20001,"微信扫码登陆失败");
        }

    }
}
