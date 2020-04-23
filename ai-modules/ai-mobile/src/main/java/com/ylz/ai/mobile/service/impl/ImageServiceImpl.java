package com.ylz.ai.mobile.service.impl;

import com.ylz.ai.common.context.BaseContextHandler;
import com.ylz.ai.mobile.constant.ImageTypeConstant;
import com.ylz.ai.mobile.entity.Image;
import com.ylz.ai.mobile.entity.ImageComment;
import com.ylz.ai.mobile.entity.UserLike;
import com.ylz.ai.mobile.mapper.ImageCommentMapper;
import com.ylz.ai.mobile.mapper.ImageMapper;
import com.ylz.ai.mobile.service.IAttentionUserService;
import com.ylz.ai.mobile.service.IImageCommentService;
import com.ylz.ai.mobile.service.IImageService;
import com.ylz.ai.common.error.ErrCodeBaseConstant;
import com.ylz.ai.common.exception.BusinessException;
import com.ylz.ai.common.query.QueryGenerator;
import com.ylz.ai.common.util.EntityUtils;
import com.ylz.ai.mobile.service.IUserLikeService;
import com.ylz.ai.mobile.vo.response.ImageInfo;
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

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Description: 照片
 * @Author: haifeng.lv
 * @Date: 2020-04-20 14:25
 */
@Service
public class ImageServiceImpl extends ServiceImpl<ImageMapper, Image> implements IImageService {
    @Resource
    private ImageCommentMapper imageCommentMapper;
    @Autowired
    private IUserLikeService userLikeService;
    @Autowired
    private IAttentionUserService attentionUserService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public IPage<ImageInfo> findImagePageList(Image image, Integer pageNo, Integer pageSize, String sortProp, String sortType) {
        QueryWrapper<Image> queryWrapper = QueryGenerator.initQueryWrapper(image, sortProp, sortType);
        queryWrapper.eq("type", ImageTypeConstant.OPEN);
        Page<Image> page = new Page<>(pageNo, pageSize);
        IPage<Image> pageList = baseMapper.selectPage(page, queryWrapper);
        IPage<ImageInfo> response = new Page<>();
        BeanUtils.copyProperties(pageList, response);
        response.setRecords(new ArrayList<>());

        if (!pageList.getRecords().isEmpty()) {
            // 查询用户喜欢列表
            List<UserLike> userLikes = userLikeService.findUserLikeByUserId(BaseContextHandler.getUserId());
            Set<String> imageIds = userLikes.stream().map(UserLike::getImageId).collect(Collectors.toSet());
            // 查询用户关注列表
            List<UserInfo> attentionUsers = attentionUserService.findAttentionUsers();
            Set<String> userIds = attentionUsers.stream().map(UserInfo::getId).collect(Collectors.toSet());
            List<ImageInfo> imageInfos = pageList.getRecords().stream().map(record -> {
                ImageInfo imageInfo = new ImageInfo();
                BeanUtils.copyProperties(record, imageInfo);
                if (imageIds.contains(imageInfo.getId())) {
                    imageInfo.setIsLike(true);
                } else {
                    imageInfo.setIsLike(false);
                }
                if (userIds.contains(imageInfo.getUploadUserId())) {
                    imageInfo.setIsAttention(true);
                } else {
                    imageInfo.setIsAttention(false);
                }

                return imageInfo;
            }).collect(Collectors.toList());

            response.setRecords(imageInfos);
        }

        return response;
    }

    @Override
    public boolean alterImageLikeById(String id, Integer number) {
        Image image = baseMapper.selectById(id);
        image.setLikeNumber(image.getLikeNumber() + number);
        return super.updateById(image);
    }

    @Override
    public ImageInfo findImageById(String id) {
        Image image = super.getById(id);
        if (null == image) {
            throw new BusinessException(ErrCodeBaseConstant.COMMON_PARAM_ERR);
        } else {
            ImageInfo imageInfo = new ImageInfo();
            BeanUtils.copyProperties(image, imageInfo);
            QueryWrapper<ImageComment> queryWrapper = new QueryWrapper();
            queryWrapper.eq("comment_image_id", image.getId());
            imageInfo.setComments(imageCommentMapper.selectList(queryWrapper));

            return imageInfo;
        }
    }

    @Override
    public boolean createImage(Image image) {
        EntityUtils.setDefaultValue(image);
        image.setUploadUserId(BaseContextHandler.getUserId());
        // 默认 0点赞
        image.setLikeNumber(0);

        return super.save(image);
    }

    @Override
    public boolean dropImageById(String id) {
        return super.removeById(id);
    }
}
