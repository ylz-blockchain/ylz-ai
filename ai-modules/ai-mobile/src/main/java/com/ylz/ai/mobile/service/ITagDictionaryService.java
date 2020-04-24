package com.ylz.ai.mobile.service;

import com.ylz.ai.mobile.entity.TagDictionary;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @Description: 标签字典
 * @Author: haifeng.lv
 * @Date: 2020-04-21 14:54
 */
public interface ITagDictionaryService extends IService<TagDictionary> {
    IPage<TagDictionary> findTagDictionaryPageList(TagDictionary tagDictionary, Integer pageNo, Integer pageSize, String sortProp, String sortType);
    List<TagDictionary> findTagDictionarys();
    boolean createTagDictionary(TagDictionary tagDictionary);
    boolean alterTagDictionaryById(TagDictionary tagDictionary);
    boolean dropTagDictionaryById(String id);
    boolean dropTagDictionaryBatch(String ids);
    TagDictionary findTagDictionaryById(String id);
}
