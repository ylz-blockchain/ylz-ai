package com.ylz.ai.mobile.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ylz.ai.common.util.JsonUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Description 配置Jackson
 * @Author haifeng.lv
 * @Date 2019/12/16 17:55
 */
@Configuration
public class JacksonConfiguration {
    @Bean
    public ObjectMapper objectMapper() {
        return JsonUtils.newObjectMapper();
    }
}
