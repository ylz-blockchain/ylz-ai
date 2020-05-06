package com.ylz.ai.mobile.service;

import com.ylz.ai.mobile.entity.ImageComment;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ylz.ai.mobile.vo.request.AddImageComment;
import com.ylz.ai.mobile.vo.request.UserCommentRequest;
import com.ylz.ai.mobile.vo.response.ImageCommentInfo;
import com.ylz.ai.mobile.vo.response.ImageCommentMinInfo;

import java.util.List;

/**
 * @Description: 照片评论
 * @Author: haifeng.lv
 * @Date: 2020-04-20 14:27
 */
public interface IImageCommentService extends IService<ImageComment> {
    IPage<ImageCommentMinInfo> findImageCommentPageList(UserCommentRequest request, Integer pageNo, Integer pageSize);
    boolean createImageComment(AddImageComment addImageComment);
    boolean dropImageCommentById(String id);
    boolean dropImageCommentBatch(String ids);
    List<ImageCommentInfo> findTagDictionaryById(String id);
    List<ImageCommentInfo> findImageCommentsByImageId(String id);
}
