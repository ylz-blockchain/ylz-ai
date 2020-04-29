package com.ylz.ai.mobile.vo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Description 用户基本信息
 * @Author haifeng.lv
 * @Date 2020/4/23 10:00
 */
@Data
@ApiModel(value = "userInfo", description = "userInfo")
public class UserInfo {
    @ApiModelProperty(value = "描述")
    private String description;
    @ApiModelProperty(value = "头像")
    private String headImage;
    private String id;
    @ApiModelProperty(value = "ip 地址")
    private String ip;
    @ApiModelProperty(value = "最后登录时间")
    private LocalDateTime lastLoginTime;
    @ApiModelProperty(value = "昵称")
    private String name;
    @ApiModelProperty(value = "手机号")
    private String phoneNumber;
    @ApiModelProperty(value = "性别")
    private Integer sex;
    @ApiModelProperty(value = "微信号")
    private String wxNumber;
}
