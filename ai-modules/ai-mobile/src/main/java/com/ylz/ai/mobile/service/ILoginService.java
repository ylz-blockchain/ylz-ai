package com.ylz.ai.mobile.service;

import com.ylz.ai.api.vo.user.FrontAuthenticationRequest;
import com.ylz.ai.mobile.entity.FrontUser;

/**
 * @Description 登录抽象接口
 * @Author haifeng.lv
 * @Date 2020/4/27 10:53
 */
public interface ILoginService {
    FrontUser login(FrontAuthenticationRequest request);
    boolean isSupport(int type);
}
