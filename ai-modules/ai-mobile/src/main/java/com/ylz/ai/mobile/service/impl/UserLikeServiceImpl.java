package com.ylz.ai.mobile.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ylz.ai.common.context.BaseContextHandler;
import com.ylz.ai.common.exception.BusinessException;
import com.ylz.ai.mobile.constant.ErrCodeConstant;
import com.ylz.ai.mobile.entity.UserLike;
import com.ylz.ai.mobile.mapper.UserLikeMapper;
import com.ylz.ai.mobile.service.IImageLinkedService;
import com.ylz.ai.mobile.service.IImageService;
import com.ylz.ai.mobile.service.IUserLikeService;
import com.ylz.ai.common.util.EntityUtils;
import com.ylz.ai.mobile.vo.response.ImageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.List;

/**
 * @Description: 用户点赞
 * @Author: haifeng.lv
 * @Date: 2020-04-22 10:46
 */
@Service
public class UserLikeServiceImpl extends ServiceImpl<UserLikeMapper, UserLike> implements IUserLikeService, IImageLinkedService {
    @Autowired
    private IImageService imageService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean createUserLike(String imageId) {
        String userId = BaseContextHandler.getUserId();
        QueryWrapper<UserLike> userLikeQueryWrapper = new QueryWrapper();
        userLikeQueryWrapper.eq("user_id", userId);
        userLikeQueryWrapper.eq("image_id", imageId);
        List<UserLike> userLikes = this.baseMapper.selectList(userLikeQueryWrapper);
        if (!userLikes.isEmpty()) {
            throw new BusinessException(ErrCodeConstant.LIKE_REPEAT_ERROR);
        }

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

    /**
     * @Description 获取当前用户点赞照片列表
     * @Author haifeng.lv
     * @param: pageNo
     * @param: pageSize
     * @Date 2020/4/24 15:14
     * @return: java.util.List<com.ylz.ai.mobile.vo.response.ImageInfo>
     */
    @Override
    public IPage<ImageInfo> findLikeImagesByCurrent(Integer pageNo, Integer pageSize) {
        Page<ImageInfo> page = new Page(pageNo, pageSize);
        IPage<ImageInfo> response = baseMapper.selectLikeImagesByCurrent(page, BaseContextHandler.getUserId());
        // 设置当前用户点赞列表的关注情况
        imageService.setLikeAndAttention(response.getRecords());
        return response;
    }

    @Override
    public Integer findImageLikeCountByUserId(String userId) {
        QueryWrapper<UserLike> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        return baseMapper.selectCount(queryWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteLink(String id) {
        QueryWrapper<UserLike> queryWrapper = new QueryWrapper();
        queryWrapper.eq("image_id", id);
        super.remove(queryWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteBatchLink(List<String> ids) {
        QueryWrapper<UserLike> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("image_id", ids);
        super.remove(queryWrapper);
    }
}
