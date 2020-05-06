package com.ylz.ai.mobile.vo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Description 用户转发基本信息
 * @Author haifeng.lv
 * @Date 2020/4/30 10:01
 */
@ApiModel(value="userImageRedirectInfo", description="userImageRedirectInfo")
@Data
public class UserImageRedirectInfo {
    private String id;
    @ApiModelProperty(value = "转发目标地")
    private String redirectPlace;
    @ApiModelProperty(value = "描述")
    private String description;
    @ApiModelProperty(value = "用户昵称")
    private String userName;
    @ApiModelProperty(value = "照片名称")
    private String imageName;
}
