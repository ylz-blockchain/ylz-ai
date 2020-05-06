package com.ylz.ai.mobile.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ylz.ai.mobile.entity.Image;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ylz.ai.mobile.vo.request.ImageRequest;
import com.ylz.ai.mobile.vo.response.ImageInfo;
import com.ylz.ai.mobile.vo.response.ImageMinInfo;
import com.ylz.ai.mobile.vo.response.ImageStatusInfo;
import org.apache.ibatis.annotations.Param;

/**
 * @Description: 照片
 * @Author: haifeng.lv
 * @Date: 2020-04-20 14:25
 */
public interface ImageMapper extends BaseMapper<Image> {

    /**
     * @Description 查询首页数据
     * @Author haifeng.lv
     * @param: page
     * @Date 2020/4/24 15:37
     * @return: com.baomidou.mybatisplus.core.metadata.IPage<com.ylz.ai.mobile.vo.response.ImageInfo>
     */
    IPage<ImageInfo> selectIndexImagePageList(@Param("page") Page<ImageInfo> page);

    /**
     * @Description 查询发现数据
     * @Author haifeng.lv
     * @param: page
     * @Date 2020/4/24 15:52
     * @return: com.baomidou.mybatisplus.core.metadata.IPage<com.ylz.ai.mobile.vo.response.ImageInfo>
     */
    IPage<ImageInfo> selectDiscoverImagePageList(@Param("page") Page<ImageInfo> page);

    /**
     * @Description 分页列表查询
     * @Author haifeng.lv
     * @param: page
     * @param: image
     * @Date 2020/4/30 14:11
     * @return: com.baomidou.mybatisplus.core.metadata.IPage<com.ylz.ai.mobile.vo.response.ImageMinInfo>
     */
    IPage<ImageMinInfo> selectImagePageList(@Param("page") Page<ImageMinInfo> page, @Param("image") ImageRequest image);

    /**
     * @Description 查询我的照片列表
     * @Author haifeng.lv
     * @param: page
     * @param: userId 用户 id
     * @Date 2020/4/30 16:41
     * @return: com.baomidou.mybatisplus.core.metadata.IPage<com.ylz.ai.mobile.vo.response.ImageInfo>
     */
    IPage<ImageInfo> selectMyImagePageList(@Param("page") Page<Image> page, @Param("userId") String userId);

    /**
     * @Description 查询照片状态
     * @Author haifeng.lv
     * @param: id
     * @Date 2020/4/30 17:52
     * @return: java.lang.Integer
     */
    ImageStatusInfo selectImageStatus(String id);
}
