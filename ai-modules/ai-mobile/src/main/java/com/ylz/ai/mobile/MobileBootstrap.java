package com.ylz.ai.mobile;

import com.ylz.ai.auth.user.EnableAuthUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @Description 手机端接口服务
 * @Author haifeng.lv
 * @Date 2019/12/16 17:59
 */
@Slf4j
@EnableCircuitBreaker
@SpringBootApplication
@EnableFeignClients({"com.ylz.ai.auth.user.feign"})
@EnableTransactionManagement
@EnableSwagger2
@EnableAuthUser
public class MobileBootstrap {
    public static void main(String[] args) throws UnknownHostException {
        ConfigurableApplicationContext application = SpringApplication.run(MobileBootstrap.class, args);
        Environment env = application.getEnvironment();
        String ip = InetAddress.getLocalHost().getHostAddress();
        String port = env.getProperty("server.port");
        log.info("\n----------------------------------------------------------\n\t" +
                "Application spring-could is running! Access URLs:\n\t" +
                "Local: \t\thttp://localhost:" + port + "/\n\t" +
                "swagger-ui: \thttp://" + ip + ":" + port + "/swagger-ui.html\n\t" +
                "----------------------------------------------------------");
    }
}
