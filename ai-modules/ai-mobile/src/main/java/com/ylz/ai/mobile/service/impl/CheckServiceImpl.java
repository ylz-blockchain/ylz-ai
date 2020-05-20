package com.ylz.ai.mobile.service.impl;

import com.ylz.ai.common.exception.BusinessException;
import com.ylz.ai.mobile.constant.ErrCodeConstant;
import com.ylz.ai.mobile.service.ICheckService;
import com.ylz.ai.mobile.weixin.WeixinTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description 检查服务
 * @Author haifeng.lv
 * @Date 2020/5/19 13:54
 */
@Service
@Slf4j
public class CheckServiceImpl implements ICheckService {
    @Autowired
    private WeixinTemplate weixinTemplate;

    @Override
    public boolean checkContent(String content) {
        return weixinTemplate.checkContent(content);
    }

    @Override
    public boolean checkImage(String path) {
        return weixinTemplate.checkImage(path);
    }
}
