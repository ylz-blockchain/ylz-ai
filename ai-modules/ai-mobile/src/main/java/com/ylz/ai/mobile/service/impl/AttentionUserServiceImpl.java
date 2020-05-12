package com.ylz.ai.mobile.service.impl;

import com.ylz.ai.common.context.BaseContextHandler;
import com.ylz.ai.mobile.constant.ErrCodeConstant;
import com.ylz.ai.mobile.entity.AttentionUser;
import com.ylz.ai.mobile.entity.FrontUser;
import com.ylz.ai.mobile.mapper.AttentionUserMapper;
import com.ylz.ai.mobile.service.IAttentionUserService;
import com.ylz.ai.common.error.ErrCodeBaseConstant;
import com.ylz.ai.common.exception.BusinessException;
import com.ylz.ai.common.query.QueryGenerator;
import com.ylz.ai.common.util.EntityUtils;
import com.ylz.ai.mobile.service.IFrontUserService;
import com.ylz.ai.mobile.vo.response.UserInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.beans.BeanUtils;
import org.springframework.transaction.annotation.Transactional;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Description: 关注用户
 * @Author: haifeng.lv
 * @Date: 2020-04-20 14:27
 */
@Service
public class AttentionUserServiceImpl extends ServiceImpl<AttentionUserMapper, AttentionUser> implements IAttentionUserService {
    @Autowired
    private IFrontUserService frontUserService;

    /**
     * @Description 查询关注用户
     * @Author haifeng.lv
     * @Date 2020/4/23 10:02
     * @return: java.util.List<com.ylz.ai.mobile.vo.response.UserInfo>
     */
    @Override
    public List<UserInfo> findAttentionUsers() {
        QueryWrapper<AttentionUser> attentionUserQueryWrapper = new QueryWrapper();
        attentionUserQueryWrapper.eq("attention_user_id", BaseContextHandler.getUserId());
        List<AttentionUser> attentionUsers = baseMapper.selectList(attentionUserQueryWrapper);
        if (attentionUsers.isEmpty()) {
            return new ArrayList<>();
        }

        QueryWrapper<FrontUser> frontUserQueryWrapper = new QueryWrapper();
        frontUserQueryWrapper.in("id", attentionUsers.stream().map(AttentionUser::getBeAttentionUserId).collect(Collectors.toList()));
        List<FrontUser> frontUsers = frontUserService.list(frontUserQueryWrapper);
        List<UserInfo> response = frontUsers.stream().map(frontUser -> {
            UserInfo userInfo = new UserInfo();
            BeanUtils.copyProperties(frontUser, userInfo);

            return userInfo;
        }).collect(Collectors.toList());
        return response;
    }

    /**
     * @Description 查询被关注用户
     * @Author haifeng.lv
     * @param: id 被关注用户 id
     * @Date 2020/4/23 10:13
     * @return: java.util.List<com.ylz.ai.mobile.vo.response.UserInfo>
     */
    @Override
    public List<UserInfo> findBeAttentionUsers(String id) {
        QueryWrapper<AttentionUser> attentionUserQueryWrapper = new QueryWrapper();
        attentionUserQueryWrapper.eq("be_attention_user_id", id);
        List<AttentionUser> attentionUsers = baseMapper.selectList(attentionUserQueryWrapper);
        if (attentionUsers.isEmpty()) {
            return new ArrayList<>();
        }

        QueryWrapper<FrontUser> frontUserQueryWrapper = new QueryWrapper();
        frontUserQueryWrapper.in("id", attentionUsers.stream().map(AttentionUser::getAttentionUserId).collect(Collectors.toList()));
        List<FrontUser> frontUsers = frontUserService.list(frontUserQueryWrapper);
        List<UserInfo> response = frontUsers.stream().map(frontUser -> {
            UserInfo userInfo = new UserInfo();
            BeanUtils.copyProperties(frontUser, userInfo);

            return userInfo;
        }).collect(Collectors.toList());
        return response;
    }

    /**
     * @Description 关注用户
     * @Author haifeng.lv
     * @param: id 被关注用户的 id
     * @Date 2020/4/23 10:24
     */
    @Override
    public boolean attentionUsers(String id) {
        String userId = BaseContextHandler.getUserId();
        if (userId.equals(id)) {
            throw new BusinessException(ErrCodeConstant.NO_ATTENTION_SELF_ERROR);
        }

        FrontUser frontUser = frontUserService.getById(id);
        if (null == frontUser) {
            throw new BusinessException(ErrCodeConstant.NO_USER_ERROR);
        }
        QueryWrapper<AttentionUser> queryWrapper = new QueryWrapper();
        queryWrapper.eq("attention_user_id", userId);
        queryWrapper.eq("be_attention_user_id", id);
        List<AttentionUser> attentionUsers = baseMapper.selectList(queryWrapper);
        if (!attentionUsers.isEmpty()) {
            throw new BusinessException(ErrCodeConstant.ATTENTION_REPEAT_ERROR);
        }

        AttentionUser attentionUser = new AttentionUser();
        EntityUtils.setDefaultValue(attentionUser);
        attentionUser.setAttentionUserId(userId);
        attentionUser.setBeAttentionUserId(id);

        return super.save(attentionUser);
    }

    /**
     * @Description 取消关注用户
     * @Author haifeng.lv
     * @param: id 被关注的用户 id
     * @Date 2020/4/23 10:29
     * @return: boolean
     */
    @Override
    public boolean cleanAttentionUsers(String id) {
        FrontUser frontUser = frontUserService.getById(id);
        if (null == frontUser) {
            throw new BusinessException(ErrCodeConstant.NO_USER_ERROR);
        }

        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("attention_user_id", BaseContextHandler.getUserId());
        queryWrapper.eq("be_attention_user_id", id);

        return super.remove(queryWrapper);
    }

    /**
     * @Description 查询用户关注情况
     * @Author haifeng.lv
     * @param: userId
     * @param: flag true 关注的用户数量 false 被关注的用户数量
     * @Date 2020/5/12 16:57
     * @return: java.lang.Integer
     */
    @Override
    public Integer findUserAttentionCountByUserId(String userId, boolean flag) {
        QueryWrapper<AttentionUser> queryWrapper = new QueryWrapper<>();
        if (flag) {
            queryWrapper.eq("attention_user_id", userId);
        } else {
            queryWrapper.eq("be_attention_user_id", userId);
        }
        return baseMapper.selectCount(queryWrapper);
    }
}
