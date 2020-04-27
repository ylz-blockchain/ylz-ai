package com.ylz.ai.mobile.weixin;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @Description
 * @Author haifeng.lv
 * @Date 2020/4/26 15:25
 */
@Configuration
public class AutoWeixinConfiguration {
    @Bean
    WeixinConfig getWeixinConfig() {
        return new WeixinConfig();
    }
}
