package com.ylz.ai.mobile.service;

/**
 * @Description 检查接口
 * @Author haifeng.lv
 * @Date 2020/5/19 13:52
 */
public interface ICheckService {
    boolean checkContent(String content);
    boolean checkImage(String path);
}
