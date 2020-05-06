package com.ylz.ai.mobile.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ylz.ai.mobile.entity.ImageTag;
import com.ylz.ai.mobile.mapper.ImageTagMapper;
import com.ylz.ai.mobile.service.IImageLinkedService;
import com.ylz.ai.mobile.service.IImageTagService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Description: 照片标签
 * @Author: haifeng.lv
 * @Date: 2020-04-26 15:02
 */
@Service
public class ImageTagServiceImpl extends ServiceImpl<ImageTagMapper, ImageTag> implements IImageTagService, IImageLinkedService {

    /**
     * @Description 级联删除
     * @Author haifeng.lv
     * @param: id 照片 id
     * @Date 2020/4/29 16:15
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteLink(String id) {
        QueryWrapper<ImageTag> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("image_id", id);
        super.remove(queryWrapper);
    }

    @Override
    public void deleteBatchLink(List<String> ids) {
        QueryWrapper<ImageTag> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("image_id", ids);
        super.remove(queryWrapper);
    }
}
