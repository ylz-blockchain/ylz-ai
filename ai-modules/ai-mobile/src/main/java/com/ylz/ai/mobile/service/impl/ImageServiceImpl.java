package com.ylz.ai.mobile.service.impl;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.ylz.ai.auth.user.service.AuthUserService;
import com.ylz.ai.common.context.BaseContextHandler;
import com.ylz.ai.common.util.JsonUtils;
import com.ylz.ai.common.util.SizeConverterUtils;
import com.ylz.ai.common.util.SortUtils;
import com.ylz.ai.mobile.constant.ErrCodeConstant;
import com.ylz.ai.mobile.constant.ImageEnableConstant;
import com.ylz.ai.mobile.constant.ImageProcessStatusConstant;
import com.ylz.ai.mobile.entity.Image;
import com.ylz.ai.mobile.entity.UserLike;
import com.ylz.ai.mobile.mapper.ImageMapper;
import com.ylz.ai.mobile.rabbitmq.sender.QueueSender;
import com.ylz.ai.mobile.rabbitmq.vo.ImageAiRequest;
import com.ylz.ai.mobile.service.*;
import com.ylz.ai.common.error.ErrCodeBaseConstant;
import com.ylz.ai.common.exception.BusinessException;
import com.ylz.ai.common.util.EntityUtils;
import com.ylz.ai.mobile.vo.request.AddImage;
import com.ylz.ai.mobile.vo.request.ImageRequest;
import com.ylz.ai.mobile.vo.response.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
import java.net.URL;
import java.net.URLConnection;
import java.time.LocalDateTime;
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
    @Autowired
    private List<IImageLinkedService> imageLinkedServices;
    @Resource
    private ImageMapper imageMapper;
    @Autowired
    @Qualifier("aiQueueSender")
    private QueueSender queueSender;

    /**
     * 上传路径
     */
    @Value("${upload.path}")
    private String uploadPath;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public IPage<ImageMinInfo> findImagePageList(ImageRequest request, Integer pageNo, Integer pageSize, String sortProp, String sortType) {
        Page<ImageMinInfo> page = new Page(pageNo, pageSize);
        List<OrderItem> orderItems = SortUtils.resolverSort(sortProp, sortType);
        orderItems.add(OrderItem.desc("im.crt_time"));
        page.setOrders(orderItems);
        IPage<ImageMinInfo> pageList = imageMapper.selectImagePageList(page, request);
        pageList.getRecords().forEach(record -> {
            record.setSize(SizeConverterUtils.BTrim.convert(Integer.parseInt(record.getSize())));
        });
        return pageList;
    }

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
        if (authUserService.setCurrentUserInfo(request)) {
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
        if (authUserService.setCurrentUserInfo(request)) {
            // 设置喜欢与关注
            setLikeAndAttention(response.getRecords());
        }
        return response;
    }

    /**
     * @Description 查询我的照片列表
     * @Author haifeng.lv
     * @param: pageNo
     * @param: pageSize
     * @Date 2020/4/30 16:41
     * @return: com.baomidou.mybatisplus.core.metadata.IPage<com.ylz.ai.mobile.vo.response.ImageInfo>
     */
    @Override
    public IPage<ImageInfo> findMyImagePageList(Integer pageNo, Integer pageSize) {
        Page<Image> page = new Page(pageNo, pageSize);
        IPage<ImageInfo> response = baseMapper.selectMyImagePageList(page, BaseContextHandler.getUserId());
        return response;
    }

    @Override
    public boolean alterImageLikeById(String id, Integer number) {
        Image image = baseMapper.selectById(id);
        image.setLikeNumber(image.getLikeNumber() + number);
        return super.updateById(image);
    }

    /**
     * @Description 修改照片识别地址 id
     * @Author haifeng.lv
     * @param: id
     * @param: recognitionVisitAddress
     * @param: status 0 失败 1 成功
     * @Date 2020/5/11 15:05
     */
    @Override
    public void alterImageRVAById(String id, String recognitionVisitAddress, Integer status) {
        Image image = baseMapper.selectById(id);
        if (null == image) {
            throw new BusinessException(ErrCodeConstant.COMMON_PARAM_ERR);
        }

        if (ImageProcessStatusConstant.PROCESSED.equals(status)) {
            // 成功
            image.setRecognitionVisitAddress(recognitionVisitAddress);
            image.setProcessStatus(ImageProcessStatusConstant.PROCESSED);
        } else {
            // 失败
            image.setProcessStatus(ImageProcessStatusConstant.FAIL);
        }
        image.setUpdTime(LocalDateTime.now());
        super.updateById(image);
    }

    /**
     * @Description 查询照片状态
     * @Author haifeng.lv
     * @param: id
     * @Date 2020/4/30 17:52
     * @return: java.lang.Integer
     */
    @Override
    public ImageStatusInfo findImageStatus(String id) {
        ImageStatusInfo imageStatusInfo = baseMapper.selectImageStatus(id);
        return imageStatusInfo;
    }

    @Override
    public ImageInfo findImageById(String id, HttpServletRequest request) throws Exception {
        Image image = super.getById(id);
        if (null == image) {
            throw new BusinessException(ErrCodeBaseConstant.COMMON_PARAM_ERR);
        } else {
            // 增加浏览次数
            image.setBrowseNumber(image.getBrowseNumber() + 1);
            super.baseMapper.updateById(image);

            ImageInfo imageInfo = new ImageInfo();
            BeanUtils.copyProperties(image, imageInfo);
            imageInfo.setUserInfo(frontUserService.findUserInfoByUserId(image.getUploadUserId()));
            // 获取用户 token
            if (authUserService.setCurrentUserInfo(request)) {
                // 设置喜欢与关注
                setLikeAndAttention(Arrays.asList(imageInfo));
            }

            // 查询评论信息
            List<ImageCommentInfo> imageCommentInfos = imageCommentService.findImageCommentsByImageId(image.getId());
            imageInfo.setComments(imageCommentInfos);
            return imageInfo;
        }
    }

    /**
     * @Description 创建照片
     * @Author haifeng.lv
     * @param: addImage
     * @Date 2020/5/6 11:30
     * @return: java.lang.String
     */
    @Override
    public String createImage(AddImage addImage) {
        Image image = new Image();
        EntityUtils.setDefaultValue(image);
        BeanUtils.copyProperties(addImage, image);
        try {
            URL url = new URL(addImage.getPrototypeVisitAddress());
            URLConnection conn = url.openConnection();
            image.setSize(conn.getContentLength());
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException(ErrCodeConstant.NO_GET_FILE_SIZE_ERROR);
        }
        // 默认 0点赞
        image.setLikeNumber(0);
        // 默认 0转发
        image.setRedirectNumber(0);
        // 默认 0浏览
        image.setBrowseNumber(0);
        image.setUploadUserId(BaseContextHandler.getUserId());
        // 默认未处理状态
        image.setProcessStatus(ImageProcessStatusConstant.NO_PROCESS);
        // 默认启用状态
        image.setIsEnable(ImageEnableConstant.ENABLE);
        super.save(image);

        ImageAiRequest imageAiRequest = new ImageAiRequest();
        imageAiRequest.setId(image.getId());
        // 原图访问地址
        imageAiRequest.setPrototypeVisitAddress(addImage.getPrototypeVisitAddress());
        // 通知消息端识别图像
        queueSender.sendMessage(JsonUtils.toJSONString(imageAiRequest));

        return image.getId();
    }

    /**
     * @Description 修改照片是否启用
     * @Author haifeng.lv
     * @param: id
     * @param: isEnable 是否启用 0 禁用 1 启用
     * @Date 2020/5/14 10:01
     */
    @Override
    public void alterImageEnable(String id, Integer isEnable) {
        Image image = baseMapper.selectById(id);
        if (null == image) {
            throw new BusinessException(ErrCodeConstant.COMMON_PARAM_ERR);
        }
        image.setIsEnable(isEnable);

        baseMapper.updateById(image);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean dropImageById(String id) {
        // 级联删除关联的照片信息
        imageLinkedServices.forEach(imageLinkedService -> {
            imageLinkedService.deleteLink(id);
        });

        return super.removeById(id);
    }

    @Override
    public boolean dropImageBatch(String ids) {
        if (StringUtils.isBlank(ids)) {
            throw new BusinessException(ErrCodeBaseConstant.COMMON_PARAM_ERR);
        } else {
            // 级联删除关联的照片信息
            imageLinkedServices.forEach(imageLinkedService -> {
                imageLinkedService.deleteBatchLink(Arrays.asList(ids.split(",")));
            });
            return super.removeByIds(Arrays.asList(ids.split(",")));
        }
    }

    @Override
    public List<Image> findImagesByIds(List<String> ids) {
        QueryWrapper<Image> queryWrapper = new QueryWrapper();
        queryWrapper.in("id", ids);
        return baseMapper.selectList(queryWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
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

    @Override
    public Integer findImageUploadCountByUserId(String userId) {
        QueryWrapper<Image> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("upload_user_id", userId);
        return baseMapper.selectCount(queryWrapper);
    }

    /**
     * @Description 设置喜欢与关注
     * @Author haifeng.lv
     * @param: imageInfos
     * @Date 2020/4/24 15:52
     */
    @Override
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
