package com.ylz.ai.mobile.service;

import java.util.List;

/**
 * @Description
 * @Author haifeng.lv
 * @Date 2020/4/29 16:14
 */
public interface IImageLinkedService {
    void deleteLink(String id);
    void deleteBatchLink(List<String> ids);
}
