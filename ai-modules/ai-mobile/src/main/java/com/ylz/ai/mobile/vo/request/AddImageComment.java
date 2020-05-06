package com.ylz.ai.mobile.vo.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @Description 添加照片评论
 * @Author haifeng.lv
 * @Date 2020/4/23 15:18
 */
@Data
@ApiModel(value="添加照片评论", description="添加照片评论")
public class AddImageComment {
    @NotNull(message = "照片 id 不可为空")
    @ApiModelProperty(value = "照片 id")
    private String ImageId;
    @ApiModelProperty(value = "评论内容")
    private String comment;
}
