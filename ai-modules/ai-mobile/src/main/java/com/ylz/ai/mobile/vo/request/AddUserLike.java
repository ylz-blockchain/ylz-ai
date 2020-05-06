package com.ylz.ai.mobile.vo.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @Description
 * @Author haifeng.lv
 * @Date 2020/4/22 10:47
 */
@Data
@ApiModel(value="添加用户点赞", description="添加用户点赞")
public class AddUserLike {
    @NotNull(message = "照片 id 不可为空")
    @ApiModelProperty(value = "照片 id")
    private String imageId;
}
