package com.ylz.ai.mobile.service;

import com.ylz.ai.mobile.entity.RecognitionType;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @Description: 识别类型
 * @Author: haifeng.lv
 * @Date: 2020-04-24 14:38
 */
public interface IRecognitionTypeService extends IService<RecognitionType> {
    IPage<RecognitionType> findRecognitionTypePageList(RecognitionType recognitionType, Integer pageNo, Integer pageSize, String sortProp, String sortType);
    List<RecognitionType> findRecognitionTypes();
    boolean createRecognitionType(RecognitionType recognitionType);
    boolean alterRecognitionTypeById(RecognitionType recognitionType);
    boolean dropRecognitionTypeById(String id);
    boolean dropRecognitionTypeBatch(String ids);
    RecognitionType findRecognitionTypeById(String id);
}
