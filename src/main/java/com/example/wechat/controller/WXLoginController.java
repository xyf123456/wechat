package com.example.wechat.controller;


import com.example.wechat.common.HttpClientUtil;
import com.example.wechat.common.IMoocJSONResult;
import com.example.wechat.common.JsonUtils;
import com.example.wechat.common.RedisOperator;
import com.example.wechat.model.WXSessionModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
/**
 * @ClassName: com.example.wechat.controller.WXLoginController
 * @Description:  微信登录控制器
 * @Author:      Administrator
 * @CreateDate: 2019/1/11 15:39
 * @UpdateUser:   Administrator
 * @Version:        1.0
 **/
@RestController
public class WXLoginController {

    @Autowired
    private RedisOperator redis;
    /**
     * @ Description: 处理微信登录的请求，通过获取登录凭证，调用后端的程序得到微信的session_key,secret
     * @params:  * @Param: code // 调用接口获取登录凭证（code）
     * @return:com.example.wechat.common.IMoocJSONResult
     **/
    @PostMapping("/wxLogin")
    public IMoocJSONResult wxLogin(String code) {

        System.out.println("wxlogin - code: " + code);
//        https://api.weixin.qq.com/sns/jscode2session?
//                                      appid=APPID&
//                                      secret=SECRET&
//                                      js_code=JSCODE&
//                                      grant_type=authorization_code

        String url = "https://api.weixin.qq.com/sns/jscode2session";
        Map<String, String> param = new HashMap<>();
        param.put("appid", "wx018999aa9e42c075");
        param.put("secret", "1cdd9c0673cc17f8a4e0340bd677682a");
        param.put("js_code", code);
        param.put("grant_type", "authorization_code");
//        通过获取临时的code，交换得到openid和session_key
        String wxResult = HttpClientUtil.doGet(url,param);
        System.out.println(wxResult);
//        得到包括用户的唯一标识（openid）及本次登录的会话密钥（session_key），将其转化为对象
        WXSessionModel model = JsonUtils.jsonToPojo(wxResult, WXSessionModel.class);
//        将openid和session_key存入到Redis当中
        redis.set("user-redis-session:"+model.getOpenid(),model.getSession_key(),1000 * 60 * 30);
        return IMoocJSONResult.ok();
    }

}
