package com.ylz.ai.auth.server.modules.user.service.impl;

import com.ylz.ai.api.vo.user.FrontAuthenticationRequest;
import com.ylz.ai.auth.server.auth.user.UserTokenHelper;
import com.ylz.ai.auth.server.feign.IFrontUserFeign;
import com.ylz.ai.auth.server.modules.user.service.IFrontUserService;
import com.ylz.ai.common.vo.AuthInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @Description
 * @Author haifeng.lv
 * @Date 2020/4/17 14:09
 */
@Service
public class FrontUserService implements IFrontUserService {
    @Autowired
    private UserTokenHelper userTokenHelper;
    @Autowired
    private IFrontUserFeign frontUserFeign;
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * @Description 获取token
     * @Author haifeng.lv
     * @param: id
     * @param: name
     * @Date 2020/4/17 14:09
     * @return: java.lang.String
     */
    @Override
    public String getToken(FrontAuthenticationRequest request) throws Exception {
        frontUserFeign.validate(request);
        if(redisTemplate.hasKey(request.getCode())) {
            return (String) redisTemplate.opsForValue().get(request.getCode());
        }

        // 获取 token
        String token = userTokenHelper.generateToken(new AuthInfo(request.getCode(), request.getName()));
        redisTemplate.opsForValue().set(request.getCode(), token, 60 * 10, TimeUnit.SECONDS);

        return token;
    }
}
