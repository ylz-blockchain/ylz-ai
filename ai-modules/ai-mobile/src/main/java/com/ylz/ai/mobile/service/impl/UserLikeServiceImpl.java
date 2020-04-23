package com.ylz.ai.mobile.service.impl;

import com.ylz.ai.common.context.BaseContextHandler;
import com.ylz.ai.mobile.entity.UserLike;
import com.ylz.ai.mobile.mapper.UserLikeMapper;
import com.ylz.ai.mobile.service.IImageService;
import com.ylz.ai.mobile.service.IUserLikeService;
import com.ylz.ai.common.error.ErrCodeBaseConstant;
import com.ylz.ai.common.exception.BusinessException;
import com.ylz.ai.common.query.QueryGenerator;
import com.ylz.ai.common.util.EntityUtils;
import com.ylz.ai.mobile.vo.request.AddUserLike;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.beans.BeanUtils;
import org.springframework.transaction.annotation.Transactional;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.Arrays;
import java.util.List;

/**
 * @Description: 用户点赞
 * @Author: haifeng.lv
 * @Date: 2020-04-22 10:46
 */
@Service
public class UserLikeServiceImpl extends ServiceImpl<UserLikeMapper, UserLike> implements IUserLikeService {
    @Autowired
    private IImageService imageService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean createUserLike(String imageId) {
        String userId = BaseContextHandler.getUserId();
        UserLike userLike = new UserLike();
        EntityUtils.setDefaultValue(userLike);
        userLike.setImageId(imageId);
        userLike.setUserId(userId);

        if (super.save(userLike)) {
            return imageService.alterImageLikeById(imageId, 1);
        } else {
            return false;
        }
    }

    /**
     * @Description 通过照片id删除
     * @Author haifeng.lv
     * @param: id 照片 id
     * @Date 2020/4/22 11:03
     * @return: boolean
     */
    @Override
    public boolean dropUserLikeByImageId(String id) {
        QueryWrapper<UserLike> userLikeQueryWrapper = new QueryWrapper();
        userLikeQueryWrapper.eq("user_id", BaseContextHandler.getUserId());
        userLikeQueryWrapper.eq("image_id", id);
        if (baseMapper.delete(userLikeQueryWrapper) > 0) {
            return imageService.alterImageLikeById(id, -1);
        } else {
            return false;
        }
    }

    /**
     * @Description 查询用户点赞列表
     * @Author haifeng.lv
     * @param: imageId 照片 id
     * @Date 2020/4/23 10:36
     * @return: java.util.List<com.ylz.ai.mobile.entity.UserLike>
     */
    @Override
    public List<UserLike> findUserLikeByUserId(String userId) {
        QueryWrapper<UserLike> queryWrapper = new QueryWrapper();
        queryWrapper.eq("user_id", userId);
        return baseMapper.selectList(queryWrapper);
    }
}
