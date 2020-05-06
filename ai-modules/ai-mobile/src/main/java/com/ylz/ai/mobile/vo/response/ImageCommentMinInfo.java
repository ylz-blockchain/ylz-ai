package com.ylz.ai.mobile.vo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Description 照片评论简略返回
 * @Author haifeng.lv
 * @Date 2020/4/30 11:02
 */
@Data
@ApiModel(value="imageCommentMinInfo", description="imageCommentMinInfo")
public class ImageCommentMinInfo {
    private String id;
    @ApiModelProperty(value = "评论")
    private String commentDescription;
    @ApiModelProperty(value = "用户昵称")
    private String userName;
    @ApiModelProperty(value = "照片名称")
    private String imageName;
}
