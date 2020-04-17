package com.ylz.ai.mobile.rabbitmq;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description rabbitmq 消息
 * @Author haifeng.lv
 * @Date 2020/4/16 17:49
 */
@Configuration
public class RabbitConfig {
    private static final String MAX_PRIORITY = "x-max-priority";
    private static final int MAX_NUMBER = 10;
    public static final String YLZ_AI_NOTICE = "ylz.ai.notice";
    public static final String YLZ_AI_BACK = "ylz.ai.back";
    private static final Map<String, Object> param = new HashMap<>();

    static {
        param.put(MAX_PRIORITY, MAX_NUMBER);
        param.put("x-message-ttl", 600 * 1000);
    }

    @Bean
    public Queue notice() {
        return new Queue(YLZ_AI_NOTICE, true, false, false, param);
    }

    @Bean
    public Queue back() {
        return new Queue(YLZ_AI_BACK, true, false, false, param);
    }
}
