package com.ylz.ai.mobile.service.impl;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.ylz.ai.auth.user.service.AuthUserService;
import com.ylz.ai.common.context.BaseContextHandler;
import com.ylz.ai.mobile.constant.ErrCodeConstant;
import com.ylz.ai.mobile.entity.Image;
import com.ylz.ai.mobile.entity.ImageComment;
import com.ylz.ai.mobile.entity.UserLike;
import com.ylz.ai.mobile.mapper.ImageCommentMapper;
import com.ylz.ai.mobile.mapper.ImageMapper;
import com.ylz.ai.mobile.service.*;
import com.ylz.ai.common.error.ErrCodeBaseConstant;
import com.ylz.ai.common.exception.BusinessException;
import com.ylz.ai.common.util.EntityUtils;
import com.ylz.ai.mobile.vo.response.ImageCommentInfo;
import com.ylz.ai.mobile.vo.response.ImageInfo;
import com.ylz.ai.mobile.vo.response.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import org.springframework.beans.BeanUtils;
import org.springframework.transaction.annotation.Transactional;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
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
    private IImageCommentService imageCommentService;
    @Autowired
    private IUserLikeService userLikeService;
    @Autowired
    private IAttentionUserService attentionUserService;
    @Autowired
    private AuthUserService authUserService;
    @Resource
    private ResourceLoader resourceLoader;
    @Autowired
    private IFrontUserService frontUserService;
    /**
     * 上传路径
     */
    @Value("${upload.path}")
    private String uploadPath;

    /**
     * @Description 查询首页数据
     * @Author haifeng.lv
     * @param: pageNo
     * @param: pageSize
     * @Date 2020/4/24 15:37
     * @return: com.baomidou.mybatisplus.core.metadata.IPage<com.ylz.ai.mobile.vo.response.ImageInfo>
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public IPage<ImageInfo> findIndexImagePageList(Integer pageNo, Integer pageSize, HttpServletRequest request) throws Exception {
        Page<ImageInfo> page = new Page(pageNo, pageSize);
        IPage<ImageInfo> response = baseMapper.selectIndexImagePageList(page);
        // 获取用户 token
        if(authUserService.setCurrentUserInfo(request)) {
            // 设置喜欢与关注
            setLikeAndAttention(response.getRecords());
        }
        return response;
    }

    /**
     * @Description 发现分页列表查询
     * @Author haifeng.lv
     * @param: pageNo
     * @param: pageSize
     * @Date 2020/4/24 15:55
     * @return: com.baomidou.mybatisplus.core.metadata.IPage<com.ylz.ai.mobile.vo.response.ImageInfo>
     */
    @Override
    public IPage<ImageInfo> findDiscoverImagePageList(Integer pageNo, Integer pageSize, HttpServletRequest request) throws Exception {
        Page<ImageInfo> page = new Page(pageNo, pageSize);
        IPage<ImageInfo> response = baseMapper.selectDiscoverImagePageList(page);
        // 获取用户 token
        if(authUserService.setCurrentUserInfo(request)) {
            // 设置喜欢与关注
            setLikeAndAttention(response.getRecords());
        }
        return response;
    }

    @Override
    public IPage<ImageInfo> findMyImagePageList(Integer pageNo, Integer pageSize) {
        Page<Image> page = new Page(pageNo, pageSize);
        QueryWrapper<Image> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("upload_user_id", BaseContextHandler.getUserId());
        page.setOrders(Arrays.asList(OrderItem.desc("crt_time")));
        IPage<Image> imageIPage = baseMapper.selectPage(page, queryWrapper);
        IPage<ImageInfo> response = new Page<>();
        BeanUtils.copyProperties(imageIPage, response);
        response.setRecords(imageIPage.getRecords().stream().map(record -> {
            ImageInfo imageInfo = new ImageInfo();
            BeanUtils.copyProperties(record, imageInfo);
            return imageInfo;
        }).collect(Collectors.toList()));

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
            // 增加浏览次数
            image.setBrowseNumber(image.getBrowseNumber() + 1);
            super.baseMapper.updateById(image);

            ImageInfo imageInfo = new ImageInfo();
            BeanUtils.copyProperties(image, imageInfo);
            imageInfo.setUserInfo(frontUserService.findUserInfoBuUserId(image.getUploadUserId()));

            // 查询评论信息
            List<ImageCommentInfo> imageCommentInfos = imageCommentService.findImageCommentsByImageId(image.getId());
            imageInfo.setComments(imageCommentInfos);
            return imageInfo;
        }
    }

    @Override
    public boolean createImage(Image image) {
        EntityUtils.setDefaultValue(image);
        image.setUploadUserId(BaseContextHandler.getUserId());
        // 默认 0点赞
        image.setLikeNumber(0);
        // 默认 0转发
        image.setRedirectNumber(0);
        // 默认 0浏览
        image.setBrowseNumber(0);
        image.setUploadUserId(BaseContextHandler.getUserId());
        try {
            File file = new File(image.getPrototypeVisitAddress());
            // 大小
            image.setSize((int) file.length());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return super.save(image);
    }

    @Override
    public boolean dropImageById(String id) {
        return super.removeById(id);
    }

    @Override
    public List<Image> findImagesByIds(List<String> ids) {
        QueryWrapper<Image> queryWrapper = new QueryWrapper();
        queryWrapper.in("id", ids);
        return baseMapper.selectList(queryWrapper);
    }

    @Override
    public void alterImageRedirectById(String imageId) {
        Image image = baseMapper.selectById(imageId);
        image.setRedirectNumber(image.getRedirectNumber() + 1);
        super.updateById(image);
    }

    @Override
    public ResponseEntity<org.springframework.core.io.Resource> findImage(String fileName) {
        try {
            return ResponseEntity.ok(resourceLoader.getResource("file:" + uploadPath + fileName));
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException(ErrCodeConstant.COMMON_PARAM_ERR);
        }
    }

    /**
     * @Description 设置喜欢与关注
     * @Author haifeng.lv
     * @param: imageInfos
     * @Date 2020/4/24 15:52
     */
    public void setLikeAndAttention(List<ImageInfo> imageInfos) {
        if (!imageInfos.isEmpty()) {
            // 查询用户喜欢列表
            List<UserLike> userLikes = userLikeService.findUserLikeByUserId(BaseContextHandler.getUserId());
            Set<String> imageIds = userLikes.stream().map(UserLike::getImageId).collect(Collectors.toSet());
            // 查询用户关注列表
            List<UserInfo> attentionUsers = attentionUserService.findAttentionUsers();
            Set<String> userIds = attentionUsers.stream().map(UserInfo::getId).collect(Collectors.toSet());
            imageInfos.forEach(record -> {
                if (imageIds.contains(record.getId())) {
                    record.setIsLike(true);
                } else {
                    record.setIsLike(false);
                }
                if (userIds.contains(record.getUserInfo().getId())) {
                    record.setIsAttention(true);
                } else {
                    record.setIsAttention(false);
                }
            });
        }
    }
}
