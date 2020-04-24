package com.ylz.ai.mobile.service;

import com.ylz.ai.mobile.entity.Image;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ylz.ai.mobile.vo.response.ImageInfo;

import java.util.List;

/**
 * @Description: 照片
 * @Author: haifeng.lv
 * @Date: 2020-04-20 14:25
 */
public interface IImageService extends IService<Image> {
    IPage<ImageInfo> findIndexImagePageList(Integer pageNo, Integer pageSize);
    IPage<ImageInfo> findDiscoverImagePageList(Integer pageNo, Integer pageSize);
    boolean alterImageLikeById(String id, Integer number);
    ImageInfo findImageById(String id);
    boolean createImage(Image image);
    boolean dropImageById(String id);
    List<Image> findImagesByIds(List<String> ids);
    void alterImageRedirectById(String imageId);
}
