package com.ylz.ai.api.vo.user;

import lombok.Data;

/**
 * @Description 前端请求信息
 * @Author haifeng.lv
 * @Date 2020/4/17 14:02
 */
@Data
public class FrontAuthenticationRequest {
    /**
     * openid
     */
    private String id;
    /**
     * 名称
     */
    private String name;
}
