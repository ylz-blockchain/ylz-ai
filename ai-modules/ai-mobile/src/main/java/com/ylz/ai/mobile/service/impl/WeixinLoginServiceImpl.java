package com.ylz.ai.mobile.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ylz.ai.api.vo.user.FrontAuthenticationRequest;
import com.ylz.ai.common.exception.BusinessException;
import com.ylz.ai.common.util.EntityUtils;
import com.ylz.ai.common.util.MD5Util;
import com.ylz.ai.mobile.constant.ErrCodeConstant;
import com.ylz.ai.mobile.constant.LoginTypeConstant;
import com.ylz.ai.mobile.constant.UserSexConstant;
import com.ylz.ai.mobile.constant.WeiXinConstant;
import com.ylz.ai.mobile.entity.FrontUser;
import com.ylz.ai.mobile.service.IFrontUserService;
import com.ylz.ai.mobile.service.ILoginService;
import com.ylz.ai.mobile.weixin.WeixinTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @Description 微信登录
 * @Author haifeng.lv
 * @Date 2020/4/27 11:15
 */
@Service
@Slf4j
public class WeixinLoginServiceImpl implements ILoginService {
    @Autowired
    private IFrontUserService frontUserService;
    @Autowired
    private WeixinTemplate weixinTemplate;

    /**
     * @Description 微信登录
     * @Author haifeng.lv
     * @param: request
     * @Date 2020/4/27 11:19
     * @return: com.ylz.ai.api.vo.user.FrontAuthenticationResponse
     */
    @Override
    public FrontUser login(FrontAuthenticationRequest request) {
        try {
            QueryWrapper<FrontUser> queryWrapper = new QueryWrapper<>();
            JSONObject result = weixinTemplate.login(request.getCode());
            log.info("微信登录用户信息: " + result);

            String open_id = (String) result.get(WeiXinConstant.OPEN_CODE);
            queryWrapper.eq("wx_number", open_id);
            List<FrontUser> frontUsers = frontUserService.list(queryWrapper);
            if (!frontUsers.isEmpty()) {
                return frontUsers.get(0);
            } else {
                FrontUser frontUser = new FrontUser();
                frontUser.setWxNumber(open_id);
                return frontUser;
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException(ErrCodeConstant.WEIXIN_LOGIN_ERROR);
        }
    }

    @Override
    public boolean isSupport(int type) {
        return type == LoginTypeConstant.WEIXIN;
    }
}
