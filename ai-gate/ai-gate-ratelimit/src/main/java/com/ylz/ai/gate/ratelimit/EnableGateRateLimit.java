package com.ylz.ai.gate.ratelimit;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @Description 开启网关限速
 * @Author haifeng.lv
 * @Date 2019/12/16 17:53
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(RateLimitAutoConfiguration.class)
@Documented
@Inherited
public @interface EnableGateRateLimit {
}
