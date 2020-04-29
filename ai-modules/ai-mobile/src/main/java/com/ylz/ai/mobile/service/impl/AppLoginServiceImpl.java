package com.ylz.ai.mobile.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ylz.ai.api.vo.user.FrontAuthenticationRequest;
import com.ylz.ai.common.util.MD5Util;
import com.ylz.ai.mobile.constant.LoginTypeConstant;
import com.ylz.ai.mobile.entity.FrontUser;
import com.ylz.ai.mobile.service.IFrontUserService;
import com.ylz.ai.mobile.service.ILoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description app 端登录
 * @Author haifeng.lv
 * @Date 2020/4/27 11:10
 */
@Service
public class AppLoginServiceImpl implements ILoginService {
    @Autowired
    private IFrontUserService frontUserService;

    @Override
    public FrontUser login(FrontAuthenticationRequest request) {
        QueryWrapper<FrontUser> queryWrapper = new QueryWrapper();
        queryWrapper.eq("username", request.getUsername());
        queryWrapper.eq("password", MD5Util.md5(request.getPassword()));
        List<FrontUser> frontUsers = frontUserService.list(queryWrapper);
        if (!frontUsers.isEmpty()) {
            return frontUsers.get(0);
        } else {
            FrontUser frontUser = new FrontUser();
            frontUser.setPassword(MD5Util.md5(request.getPassword()));
            frontUser.setUsername(request.getUsername());
            return frontUser;
        }
    }

    @Override
    public boolean isSupport(int type) {
        return type == LoginTypeConstant.USERNAME;
    }
}
