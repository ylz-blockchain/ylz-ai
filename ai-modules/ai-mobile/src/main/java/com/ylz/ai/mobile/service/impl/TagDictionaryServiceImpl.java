package com.ylz.ai.mobile.service.impl;

import com.ylz.ai.mobile.entity.TagDictionary;
import com.ylz.ai.mobile.mapper.TagDictionaryMapper;
import com.ylz.ai.mobile.service.ITagDictionaryService;
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
import java.util.List;

/**
 * @Description: 标签字典
 * @Author: haifeng.lv
 * @Date: 2020-04-21 14:54
 */
@Service
public class TagDictionaryServiceImpl extends ServiceImpl<TagDictionaryMapper, TagDictionary> implements ITagDictionaryService {
    @Override
    @Transactional(rollbackFor = Exception.class)
    public IPage<TagDictionary> findTagDictionaryPageList(TagDictionary tagDictionary, Integer pageNo, Integer pageSize, String sortProp, String sortType) {
        QueryWrapper<TagDictionary> queryWrapper = QueryGenerator.initQueryWrapper(tagDictionary, sortProp, sortType);
        Page<TagDictionary> page = new Page<>(pageNo, pageSize);
        IPage<TagDictionary> pageList = baseMapper.selectPage(page, queryWrapper);
        return pageList;
    }

    @Override
    public List<TagDictionary> findTagDictionarys() {
        return baseMapper.selectList(null);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean createTagDictionary(TagDictionary tagDictionary) {
        EntityUtils.setDefaultValue(tagDictionary);
        return super.save(tagDictionary);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean alterTagDictionaryById(TagDictionary tagDictionary) {
        TagDictionary tagDictionaryEntity = baseMapper.selectById(tagDictionary.getId());
        if(tagDictionaryEntity == null) {
            throw new BusinessException(ErrCodeBaseConstant.COMMON_PARAM_ERR);
        } else {
            BeanUtils.copyProperties(tagDictionary, tagDictionaryEntity);
        }
        EntityUtils.setDefaultValue(tagDictionaryEntity);
        return super.updateById(tagDictionaryEntity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean dropTagDictionaryById(String id) {
        return super.removeById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean dropTagDictionaryBatch(String ids) {
        if(StringUtils.isBlank(ids)) {
            throw new BusinessException(ErrCodeBaseConstant.COMMON_PARAM_ERR);
        } else {
            return super.removeByIds(Arrays.asList(ids.split(",")));
        }
    }

    @Override
    public TagDictionary findTagDictionaryById(String id) {
        TagDictionary tagDictionary = super.getById(id);
        if (null == tagDictionary) {
            throw new BusinessException(ErrCodeBaseConstant.COMMON_PARAM_ERR);
        } else {
            return tagDictionary;
        }
    }
}
