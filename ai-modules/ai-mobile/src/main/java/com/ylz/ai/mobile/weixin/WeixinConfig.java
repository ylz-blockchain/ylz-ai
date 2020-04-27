package com.ylz.ai.mobile.weixin;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;

/**
 * @Description 微信基本信息配置类
 * @Author haifeng.lv
 * @Date 2020/4/26 15:24
 */
@Data
public class WeixinConfig {
    @Value("${weixin.appid}")
    private String appId;
    @Value("${weixin.secret}")
    private String secret;
    @Value("${weixin.grant_type}")
    private String grantType;
}
