package com.ylz.ai.mobile.vo.request;

import lombok.Data;

/**
 * @Description 请求用户转发
 * @Author haifeng.lv
 * @Date 2020/4/30 10:49
 */
@Data
public class UserImageRedirectRequest {
    /**
     * 转发地
     */
    private String redirectPlace;
    /**
     * 描述
     */
    private String description;
}
