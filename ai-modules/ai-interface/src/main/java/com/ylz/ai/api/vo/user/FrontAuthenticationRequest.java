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
     * 登录时获取的 code
     */
    private String code;
    /**
     * 名称
     */
    private String name;
}
