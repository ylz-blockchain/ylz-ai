package com.ylz.ai.api.vo.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Description 前端登录验证返回
 * @Author haifeng.lv
 * @Date 2020/4/27 11:01
 */
@Data
@ApiModel(value = "前端验证返回", description = "前端验证返回")
public class FrontAuthenticationResponse {
    private String id;
    @ApiModelProperty(value = "昵称")
    private String name;
    @ApiModelProperty(value = "性别 0 男 1 女")
    private String sex;
    @ApiModelProperty(value = "头像")
    private String headImage;
    @ApiModelProperty(value = "微信号(open_id)")
    private String wxNumber;
    @ApiModelProperty(value = "手机号")
    private String phoneNumber;
    @ApiModelProperty(value = "qq号")
    private String qqNumber;
    @ApiModelProperty(value = "最后登录时间")
    private LocalDateTime lastLoginTime;
    @ApiModelProperty(value = "描述")
    private String description;
    private String token;
}
