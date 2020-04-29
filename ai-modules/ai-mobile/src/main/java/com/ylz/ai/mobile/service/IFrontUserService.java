package com.ylz.ai.mobile.service;

import com.ylz.ai.api.vo.user.FrontAuthenticationRequest;
import com.ylz.ai.api.vo.user.FrontAuthenticationResponse;
import com.ylz.ai.mobile.entity.FrontUser;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ylz.ai.mobile.vo.request.AddFrontUser;
import com.ylz.ai.mobile.vo.response.UserInfo;

import javax.servlet.http.HttpServletRequest;

/**
 * @Description: 前端用户
 * @Author: haifeng.lv
 * @Date: 2020-04-17 14:22
 */
public interface IFrontUserService extends IService<FrontUser> {
    boolean alterFrontUserById(AddFrontUser addFrontUser);
    FrontAuthenticationResponse login(FrontAuthenticationRequest request, HttpServletRequest httpServletRequest);
    UserInfo findUserInfoBuUserId(String id);
}
