package com.ylz.ai.mobile.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ylz.ai.mobile.entity.UserLike;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ylz.ai.mobile.vo.response.ImageInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Description: 用户点赞
 * @Author: haifeng.lv
 * @Date: 2020-04-22 10:46
 */
public interface UserLikeMapper extends BaseMapper<UserLike> {

    /**
     * @Description 获取当前用户点赞照片列表
     * @Author haifeng.lv
     * @param: userId
     * @param: pageNo
     * @param: pageSize
     * @Date 2020/4/24 15:14
     * @return: java.util.List<com.ylz.ai.mobile.vo.response.ImageInfo>
     */
    IPage<ImageInfo> selectLikeImagesByCurrent(@Param("page") Page<ImageInfo> page, @Param("userId") String userId);
}
