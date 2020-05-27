package com.ylz.ai.mobile.service.impl;

import com.lvhaifeng.mybatis.query.QueryHelper;
import com.ylz.ai.mobile.entity.RecognitionType;
import com.ylz.ai.mobile.mapper.RecognitionTypeMapper;
import com.ylz.ai.mobile.service.IRecognitionTypeService;
import com.ylz.ai.common.error.ErrCodeBaseConstant;
import com.ylz.ai.common.exception.BusinessException;
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
 * @Description: 识别类型
 * @Author: haifeng.lv
 * @Date: 2020-04-24 14:38
 */
@Service
public class RecognitionTypeServiceImpl extends ServiceImpl<RecognitionTypeMapper, RecognitionType> implements IRecognitionTypeService {
    @Override
    @Transactional(rollbackFor = Exception.class)
    public IPage<RecognitionType> findRecognitionTypePageList(RecognitionType recognitionType, Integer pageNo, Integer pageSize, String sortProp, String sortType) {
        QueryWrapper<RecognitionType> queryWrapper = QueryHelper.initQueryWrapper(recognitionType, sortProp, sortType);
        Page<RecognitionType> page = new Page<>(pageNo, pageSize);
        IPage<RecognitionType> pageList = baseMapper.selectPage(page, queryWrapper);
        return pageList;
    }

    @Override
    public List<RecognitionType> findRecognitionTypes() {
        return baseMapper.selectList(null);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean createRecognitionType(RecognitionType recognitionType) {
        EntityUtils.setDefaultValue(recognitionType);
        return super.save(recognitionType);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean alterRecognitionTypeById(RecognitionType recognitionType) {
        RecognitionType recognitionTypeEntity = baseMapper.selectById(recognitionType.getId());
        if(recognitionTypeEntity == null) {
            throw new BusinessException(ErrCodeBaseConstant.COMMON_PARAM_ERR);
        } else {
            BeanUtils.copyProperties(recognitionType, recognitionTypeEntity);
        }
        EntityUtils.setDefaultValue(recognitionTypeEntity);
        return super.updateById(recognitionTypeEntity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean dropRecognitionTypeById(String id) {
        return super.removeById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean dropRecognitionTypeBatch(String ids) {
        if(StringUtils.isBlank(ids)) {
            throw new BusinessException(ErrCodeBaseConstant.COMMON_PARAM_ERR);
        } else {
            return super.removeByIds(Arrays.asList(ids.split(",")));
        }
    }

    @Override
    public RecognitionType findRecognitionTypeById(String id) {
        RecognitionType recognitionType = super.getById(id);
        if (null == recognitionType) {
            throw new BusinessException(ErrCodeBaseConstant.COMMON_PARAM_ERR);
        } else {
            return recognitionType;
        }
    }
}
