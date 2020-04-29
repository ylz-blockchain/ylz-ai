package com.ylz.ai.mobile.vo.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Description 添加或修改前端用户
 * @Author haifeng.lv
 * @Date 2020/4/27 16:23
 */
@Data
@ApiModel(value="添加或修改前端用户", description="添加或修改前端用户")
public class AddFrontUser {
    /**描述*/
    @ApiModelProperty(value = "描述")
    private String description;
    /**headImage*/
    @ApiModelProperty(value = "头像")
    private String headImage;
    /**昵称*/
    @ApiModelProperty(value = "昵称")
    private String name;
    /**password*/
    @ApiModelProperty(value = "密码")
    private String password;
    /**phoneNumber*/
    @ApiModelProperty(value = "手机号")
    private String phoneNumber;
    /**qqNumber*/
    @ApiModelProperty(value = "qq 号")
    private String qqNumber;
    /**0 男 1 女*/
    @ApiModelProperty(value = "性别 0 男 1 女")
    private Integer sex;
    /**登录名*/
    @ApiModelProperty(value = "登录名")
    private String username;
    /**wxNumber*/
    @ApiModelProperty(value = "微信号")
    private String wxNumber;
}
