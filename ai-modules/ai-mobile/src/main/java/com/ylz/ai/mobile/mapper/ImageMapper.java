package com.ylz.ai.mobile.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ylz.ai.mobile.entity.Image;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ylz.ai.mobile.vo.response.ImageInfo;
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
}
