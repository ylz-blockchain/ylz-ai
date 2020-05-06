package com.ylz.ai.mobile.vo.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @Description 添加照片转发
 * @Author haifeng.lv
 * @Date 2020/4/24 16:08
 */
@Data
@ApiModel(value = "addImageRedirect", description = "addImageRedirect")
public class AddImageRedirect {
    @ApiModelProperty(value = "图像 id")
    @NotNull(message = "图像 id不得为空")
    private String imageId;
    @ApiModelProperty(value = "转发目标地")
    private String redirectPlace;
    @ApiModelProperty(value = "描述")
    private String description;
}
