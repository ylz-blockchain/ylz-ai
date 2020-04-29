package com.ylz.ai.api.vo.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Description 前端请求信息
 * @Author haifeng.lv
 * @Date 2020/4/17 14:02
 */
@Data
@ApiModel(value = "前端验证请求", description = "前端验证请求")
public class FrontAuthenticationRequest {
    @ApiModelProperty(value = "微信 code")
    private String code;
    @ApiModelProperty(value = "用户名")
    private String username;
    @ApiModelProperty(value = "密码")
    private String password;
    @ApiModelProperty(value = "登录类型 0 微信 1 手机号 2 用户名 3 QQ")
    private int type;
    @ApiModelProperty(value = "手机号")
    private String phoneNumber;
}
