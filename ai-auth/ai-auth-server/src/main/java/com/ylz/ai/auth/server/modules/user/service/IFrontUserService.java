package com.ylz.ai.auth.server.modules.user.service;

import com.ylz.ai.api.vo.user.FrontAuthenticationRequest;
import com.ylz.ai.api.vo.user.FrontAuthenticationResponse;

/**
 * @Description
 * @Author haifeng.lv
 * @Date 2020/4/17 14:08
 */
public interface IFrontUserService {
    FrontAuthenticationResponse getToken(FrontAuthenticationRequest request) throws Exception;
}
