package com.ylz.ai.mobile.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.ylz.ai.mobile.entity.FrontUser;
import com.ylz.ai.mobile.mapper.FrontUserMapper;
import com.ylz.ai.mobile.service.IFrontUserService;
import com.ylz.ai.common.error.ErrCodeBaseConstant;
import com.ylz.ai.common.exception.BusinessException;
import com.ylz.ai.common.query.QueryGenerator;
import com.ylz.ai.common.util.EntityUtils;
import com.ylz.ai.mobile.weixin.WeixinTemplate;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.beans.BeanUtils;
import org.springframework.transaction.annotation.Transactional;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Arrays;

/**
 * @Description: 前端用户
 * @Author: haifeng.lv
 * @Date: 2020-04-17 14:22
 */
@Service
public class FrontUserServiceImpl extends ServiceImpl<FrontUserMapper, FrontUser> implements IFrontUserService {
    @Autowired
    private WeixinTemplate weixinTemplate;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public IPage<FrontUser> findFrontUserPageList(FrontUser frontUser, Integer pageNo, Integer pageSize, String sortProp, String sortType) {
        QueryWrapper<FrontUser> queryWrapper = QueryGenerator.initQueryWrapper(frontUser, sortProp, sortType);
        Page<FrontUser> page = new Page<>(pageNo, pageSize);
        IPage<FrontUser> pageList = baseMapper.selectPage(page, queryWrapper);
        return pageList;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean createFrontUser(FrontUser frontUser) {
        EntityUtils.setDefaultValue(frontUser);
        return super.save(frontUser);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean alterFrontUserById(FrontUser frontUser) {
        FrontUser frontUserEntity = baseMapper.selectById(frontUser.getId());
        if (frontUserEntity == null) {
            throw new BusinessException(ErrCodeBaseConstant.COMMON_PARAM_ERR);
        } else {
            BeanUtils.copyProperties(frontUser, frontUserEntity);
        }
        EntityUtils.setDefaultValue(frontUserEntity);
        return super.updateById(frontUserEntity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean dropFrontUserById(String id) {
        return super.removeById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean dropFrontUserBatch(String ids) {
        if (StringUtils.isBlank(ids)) {
            throw new BusinessException(ErrCodeBaseConstant.COMMON_PARAM_ERR);
        } else {
            return super.removeByIds(Arrays.asList(ids.split(",")));
        }
    }

    @Override
    public FrontUser findFrontUserById(String id) {
        FrontUser frontUser = super.getById(id);
        if (null == frontUser) {
            throw new BusinessException(ErrCodeBaseConstant.COMMON_PARAM_ERR);
        } else {
            return frontUser;
        }
    }

    /**
     * @Description 验证信息(没有则创建)
     * @Author haifeng.lv
     * @param: code 登录时获取的 code
     * @param: name
     * @Date 2020/4/17 14:25
     */
    @Override
    public void validate(String code, String name, HttpServletRequest httpServletRequest) {
        // 获取微信返回信息
        JSONObject wxResult = weixinTemplate.login(code);
        String id = (String) wxResult.get("openid");

        FrontUser frontUser = baseMapper.selectById(id);

        if (null == frontUser) {
            frontUser = new FrontUser();
            frontUser.setId(id);
            EntityUtils.setDefaultValue(frontUser);
            frontUser.setName(name);
            // 默认为男
            frontUser.setSex(0);
            frontUser.setIp(httpServletRequest.getRemoteAddr());
            frontUser.setLastLoginTime(LocalDateTime.now());
            baseMapper.insert(frontUser);
        } else {
            frontUser.setLastLoginTime(LocalDateTime.now());
            baseMapper.updateById(frontUser);
        }

    }
}
