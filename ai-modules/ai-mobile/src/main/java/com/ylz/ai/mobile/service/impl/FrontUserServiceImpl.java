package com.ylz.ai.mobile.service.impl;

import com.ylz.ai.api.vo.user.FrontAuthenticationRequest;
import com.ylz.ai.api.vo.user.FrontAuthenticationResponse;
import com.ylz.ai.common.context.BaseContextHandler;
import com.ylz.ai.common.util.MD5Util;
import com.ylz.ai.mobile.constant.ErrCodeConstant;
import com.ylz.ai.mobile.constant.UserSexConstant;
import com.ylz.ai.mobile.entity.FrontUser;
import com.ylz.ai.mobile.factory.LoginFactory;
import com.ylz.ai.mobile.mapper.FrontUserMapper;
import com.ylz.ai.mobile.service.IFrontUserService;
import com.ylz.ai.common.error.ErrCodeBaseConstant;
import com.ylz.ai.common.exception.BusinessException;
import com.ylz.ai.common.util.EntityUtils;
import com.ylz.ai.mobile.service.ILoginService;
import com.ylz.ai.mobile.vo.request.AddFrontUser;
import com.ylz.ai.mobile.vo.response.UserInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.beans.BeanUtils;
import org.springframework.transaction.annotation.Transactional;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

/**
 * @Description: 前端用户
 * @Author: haifeng.lv
 * @Date: 2020-04-17 14:22
 */
@Service
public class FrontUserServiceImpl extends ServiceImpl<FrontUserMapper, FrontUser> implements IFrontUserService {
    @Autowired
    private LoginFactory loginFactory;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean alterFrontUserById(AddFrontUser addFrontUser) {
        FrontUser frontUserEntity = baseMapper.selectById(BaseContextHandler.getUserId());
        if (frontUserEntity == null) {
            throw new BusinessException(ErrCodeBaseConstant.COMMON_PARAM_ERR);
        } else {
            BeanUtils.copyProperties(addFrontUser, frontUserEntity);
        }
        EntityUtils.setDefaultValue(frontUserEntity);
        if(StringUtils.isNotBlank(frontUserEntity.getPassword())) {
            frontUserEntity.setPassword(MD5Util.md5(frontUserEntity.getPassword()));
        }

        return super.updateById(frontUserEntity);
    }

    /**
     * @Description 验证信息(没有则创建)
     * @Author haifeng.lv
     * @param: code 登录时获取的 code
     * @param: name
     * @Date 2020/4/17 14:25
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public FrontAuthenticationResponse login(FrontAuthenticationRequest request, HttpServletRequest httpServletRequest) {
        ILoginService loginService = loginFactory.createLoginService(request.getType());
        if (null == loginService) {
            throw new BusinessException(ErrCodeConstant.COMMON_PARAM_ERR);
        }
        FrontUser frontUser = loginService.login(request);
        FrontAuthenticationResponse response = new FrontAuthenticationResponse();
        frontUser.setIp(httpServletRequest.getRemoteAddr());
        frontUser.setLastLoginTime(LocalDateTime.now());

        if (StringUtils.isEmpty(frontUser.getId())) {
            EntityUtils.setDefaultValue(frontUser);
            // 设置默认属性
            frontUser.setName("");
            frontUser.setQqNumber("");
            frontUser.setSex(UserSexConstant.Man);
            baseMapper.insert(frontUser);
        } else {
            baseMapper.updateById(frontUser);
        }
        BeanUtils.copyProperties(frontUser, response);

        return response;
    }

    @Override
    public UserInfo findUserInfoBuUserId(String id) {
        FrontUser frontUser = baseMapper.selectById(id);
        UserInfo userInfo = new UserInfo();
        BeanUtils.copyProperties(frontUser, userInfo);

        return userInfo;
    }
}
