package com.ylz.ai.mobile.mapper;

import com.ylz.ai.mobile.entity.ImageComment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ylz.ai.mobile.vo.response.ImageCommentInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Description: 照片评论
 * @Author: haifeng.lv
 * @Date: 2020-04-20 14:27
 */
public interface ImageCommentMapper extends BaseMapper<ImageComment> {

    /**
     * @Description 查询照片评论
     * @Author haifeng.lv
     * @param: id 照片 id
     * @Date 2020/4/28 17:11
     * @return: java.util.List<com.ylz.ai.mobile.vo.response.ImageCommentInfo>
     */
    List<ImageCommentInfo> selectImageCommentsByImageId(@Param("id") String id);
}
