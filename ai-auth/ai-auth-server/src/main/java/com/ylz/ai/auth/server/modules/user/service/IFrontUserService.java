package com.ylz.ai.auth.server.modules.user.service;

import com.ylz.ai.api.vo.user.FrontAuthenticationRequest;

/**
 * @Description
 * @Author haifeng.lv
 * @Date 2020/4/17 14:08
 */
public interface IFrontUserService {
    String getToken(FrontAuthenticationRequest request) throws Exception;
}
