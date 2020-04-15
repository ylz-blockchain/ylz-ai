package com.ylz.ai.gate.ratelimit.config.repository.limiter;

import com.ylz.ai.gate.ratelimit.config.Rate;
import com.ylz.ai.gate.ratelimit.config.properties.RateLimitProperties.Policy;

/**
 * @Description 限流器
 * @Author haifeng.lv
 * @Date 2019/12/16 17:52
 */
public interface RateLimiter {
    Rate consume(Policy policy, String key);
}
