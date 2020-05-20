package com.ylz.ai.mobile.service.impl;

import com.ylz.ai.common.context.BaseContextHandler;
import com.ylz.ai.mobile.constant.ErrCodeConstant;
import com.ylz.ai.mobile.entity.ImageComment;
import com.ylz.ai.mobile.mapper.ImageCommentMapper;
import com.ylz.ai.mobile.service.ICheckService;
import com.ylz.ai.mobile.service.IImageCommentService;
import com.ylz.ai.common.error.ErrCodeBaseConstant;
import com.ylz.ai.common.exception.BusinessException;
import com.ylz.ai.common.util.EntityUtils;
import com.ylz.ai.mobile.service.IImageLinkedService;
import com.ylz.ai.mobile.vo.request.AddImageComment;
import com.ylz.ai.mobile.vo.request.UserCommentRequest;
import com.ylz.ai.mobile.vo.response.ImageCommentInfo;
import com.ylz.ai.mobile.vo.response.ImageCommentMinInfo;
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
import java.util.stream.Collectors;

/**
 * @Description: 照片评论
 * @Author: haifeng.lv
 * @Date: 2020-04-20 14:27
 */
@Service
public class ImageCommentServiceImpl extends ServiceImpl<ImageCommentMapper, ImageComment> implements IImageCommentService, IImageLinkedService {
    @Autowired
    private ICheckService checkService;

    @Override
    public IPage<ImageCommentMinInfo> findImageCommentPageList(UserCommentRequest request, Integer pageNo, Integer pageSize) {
        Page<ImageCommentMinInfo> page = new Page<>(pageNo, pageSize);
        IPage<ImageCommentMinInfo> pageList = baseMapper.selectImageCommentPageList(page, request);
        return pageList;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean createImageComment(AddImageComment addImageComment) {
        // 检查内容是否合法
        boolean flag = checkService.checkContent(addImageComment.getComment());
        if(!flag) {
            throw new BusinessException(ErrCodeConstant.CONTENT_NO_ALLOW_ERR);
        }

        ImageComment imageComment = new ImageComment();
        EntityUtils.setDefaultValue(imageComment);
        // 照片评论
        imageComment.setCommentDescription(addImageComment.getComment());
        imageComment.setCommentUserId(BaseContextHandler.getUserId());
        // 照片 id
        imageComment.setCommentImageId(addImageComment.getImageId());

        return super.save(imageComment);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean dropImageCommentById(String id) {
        return super.removeById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean dropImageCommentBatch(String ids) {
        if(StringUtils.isBlank(ids)) {
            throw new BusinessException(ErrCodeBaseConstant.COMMON_PARAM_ERR);
        } else {
            return super.removeByIds(Arrays.asList(ids.split(",")));
        }
    }

    /**
     * @Description 照片评论-通过照片 id查询
     * @Author haifeng.lv
     * @param: id 照片 id
     * @Date 2020/4/23 15:13
     * @return: java.util.List<com.ylz.ai.mobile.entity.ImageComment>
     */
    @Override
    public List<ImageCommentInfo> findTagDictionaryById(String id) {
        QueryWrapper<ImageComment> queryWrapper = new QueryWrapper();
        queryWrapper.eq("comment_image_id", id);
        List<ImageComment> imageComments = baseMapper.selectList(queryWrapper);
        List<ImageCommentInfo> response = imageComments.stream().map(imageComment -> {
            ImageCommentInfo imageCommentInfo = new ImageCommentInfo();
            BeanUtils.copyProperties(imageComment, imageCommentInfo);

            return imageCommentInfo;
        }).collect(Collectors.toList());

        return response;
    }

    /**
     * @Description 查询照片评论
     * @Author haifeng.lv
     * @param: id 照片 id
     * @Date 2020/4/28 17:10
     * @return: java.util.List<com.ylz.ai.mobile.vo.response.ImageCommentInfo>
     */
    @Override
    public List<ImageCommentInfo> findImageCommentsByImageId(String id) {
        return baseMapper.selectImageCommentsByImageId(id);
    }

    /**
     * @Description 级联删除
     * @Author haifeng.lv
     * @param: id 照片 id
     * @Date 2020/4/29 16:15
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteLink(String id) {
        QueryWrapper<ImageComment> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("comment_image_id", id);
        super.remove(queryWrapper);
    }

    @Override
    public void deleteBatchLink(List<String> ids) {
        QueryWrapper<ImageComment> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("comment_image_id", ids);
        super.remove(queryWrapper);
    }
}
