package com.ylz.ai.gate;

import com.ylz.ai.auth.client.EnableAuthClient;
import com.ylz.ai.gate.ratelimit.EnableGateRateLimit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @Description 网关
 * @Author haifeng.lv
 * @Date 2019/12/16 17:55
 */
@Slf4j
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@EnableZuulProxy
@EnableAuthClient
@EnableFeignClients({"com.ylz.ai.auth.client.feign"})
@EnableScheduling
@EnableGateRateLimit
public class GateBootstrap {
    public static void main(String[] args) {
        SpringApplication.run(GateBootstrap.class, args);
        log.info("\n----------------------------------------------------------\n\t" +
                "Application spring-could is running! \n\t" +
                "----------------------------------------------------------");
    }
}
