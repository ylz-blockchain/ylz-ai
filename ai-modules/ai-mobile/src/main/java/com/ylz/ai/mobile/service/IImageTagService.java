package com.ylz.ai.mobile.service;

import com.ylz.ai.mobile.entity.ImageTag;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @Description: 照片标签
 * @Author: haifeng.lv
 * @Date: 2020-04-20 14:26
 */
public interface IImageTagService extends IService<ImageTag> {
    IPage<ImageTag> findImageTagPageList(ImageTag imageTag, Integer pageNo, Integer pageSize, String sortProp, String sortType);
    boolean createImageTag(ImageTag imageTag);
    boolean alterImageTagById(ImageTag imageTag);
    boolean dropImageTagById(String id);
    boolean dropImageTagBatch(String ids);
    ImageTag findImageTagById(String id);
}
