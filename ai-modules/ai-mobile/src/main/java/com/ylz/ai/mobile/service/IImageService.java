package com.ylz.ai.mobile.service;

import com.ylz.ai.mobile.entity.Image;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ylz.ai.mobile.vo.response.ImageInfo;

/**
 * @Description: 照片
 * @Author: haifeng.lv
 * @Date: 2020-04-20 14:25
 */
public interface IImageService extends IService<Image> {
    IPage<ImageInfo> findImagePageList(Integer pageNo, Integer pageSize, String sortProp, String sortType);
    boolean alterImageLikeById(String id, Integer number);
    ImageInfo findImageById(String id);
    boolean createImage(Image image);
    boolean dropImageById(String id);
}
