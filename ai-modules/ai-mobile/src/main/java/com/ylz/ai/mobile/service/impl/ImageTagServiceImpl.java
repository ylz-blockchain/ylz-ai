package com.ylz.ai.mobile.service.impl;

import com.ylz.ai.mobile.entity.ImageTag;
import com.ylz.ai.mobile.mapper.ImageTagMapper;
import com.ylz.ai.mobile.service.IImageTagService;
import com.ylz.ai.common.error.ErrCodeBaseConstant;
import com.ylz.ai.common.exception.BusinessException;
import com.ylz.ai.common.query.QueryGenerator;
import com.ylz.ai.common.util.EntityUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import org.springframework.beans.BeanUtils;
import org.springframework.transaction.annotation.Transactional;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.Arrays;

/**
 * @Description: 照片标签
 * @Author: haifeng.lv
 * @Date: 2020-04-20 14:26
 */
@Service
public class ImageTagServiceImpl extends ServiceImpl<ImageTagMapper, ImageTag> implements IImageTagService {
    @Override
    @Transactional(rollbackFor = Exception.class)
    public IPage<ImageTag> findImageTagPageList(ImageTag imageTag, Integer pageNo, Integer pageSize, String sortProp, String sortType) {
        QueryWrapper<ImageTag> queryWrapper = QueryGenerator.initQueryWrapper(imageTag, sortProp, sortType);
        Page<ImageTag> page = new Page<>(pageNo, pageSize);
        IPage<ImageTag> pageList = baseMapper.selectPage(page, queryWrapper);
        return pageList;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean createImageTag(ImageTag imageTag) {
        EntityUtils.setDefaultValue(imageTag);
        return super.save(imageTag);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean alterImageTagById(ImageTag imageTag) {
        ImageTag imageTagEntity = baseMapper.selectById(imageTag.getId());
        if(imageTagEntity == null) {
            throw new BusinessException(ErrCodeBaseConstant.COMMON_PARAM_ERR);
        } else {
            BeanUtils.copyProperties(imageTag, imageTagEntity);
        }
        EntityUtils.setDefaultValue(imageTagEntity);
        return super.updateById(imageTagEntity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean dropImageTagById(String id) {
        return super.removeById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean dropImageTagBatch(String ids) {
        if(StringUtils.isBlank(ids)) {
            throw new BusinessException(ErrCodeBaseConstant.COMMON_PARAM_ERR);
        } else {
            return super.removeByIds(Arrays.asList(ids.split(",")));
        }
    }

    @Override
    public ImageTag findImageTagById(String id) {
        ImageTag imageTag = super.getById(id);
        if (null == imageTag) {
            throw new BusinessException(ErrCodeBaseConstant.COMMON_PARAM_ERR);
        } else {
            return imageTag;
        }
    }
}
