package com.ylz.ai.mobile.weixin;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * @Description 微信操作模板
 * @Author haifeng.lv
 * @Date 2020/4/26 15:26
 */
@Component
public class WeixinTemplate {
    @Autowired
    private WeixinConfig weixinConfig;
    @Autowired
    private RestTemplate restTemplate;

    /**
     * @Description 微信登录
     * @Author haifeng.lv
     * @param: code 登录时获取的 code
     * @Date 2020/4/26 15:31
     * @return: com.alibaba.fastjson.JSONObject
     */
    public JSONObject login(String code) {
        // https://api.weixin.qq.com/sns/jscode2session?appid=APPID&secret=SECRET&js_code=JSCODE&grant_type=authorization_code
        return restTemplate.getForEntity("https://api.weixin.qq.com/sns/jscode2session?appid=" + weixinConfig.getAppId() +
                "&secret=" + weixinConfig.getSecret() +
                "&js_code=" + code +
                "&grant_type=" + weixinConfig.getGrantType(), JSONObject.class).getBody();
    }

}
