package com.ylz.ai.auth.user.configuration;

import com.ylz.ai.auth.user.config.AuthUserConfig;
import com.ylz.ai.common.hystrix.BaseHystrixConcurrencyStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @Description 授权配置
 * @Author haifeng.lv
 * @Date 2019/12/16 17:30
 */
@Configuration
@ComponentScan({"com.ylz.ai.auth.user"})
public class AutoUserConfiguration {
    @Bean
    AuthUserConfig getUserAuthConfig() {
        return new AuthUserConfig();
    }

    @Bean
    public BaseHystrixConcurrencyStrategy newBaseHystrixConcurrencyStrategy() {
        return new BaseHystrixConcurrencyStrategy();
    }
}
