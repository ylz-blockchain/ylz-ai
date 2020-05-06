package com.ylz.ai.mobile.vo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Description 照片评论详情
 * @Author haifeng.lv
 * @Date 2020/4/23 15:15
 */
@Data
@ApiModel(value = "imageCommentInfo", description = "imageCommentInfo")
public class ImageCommentInfo {
    @ApiModelProperty(value = "评论内容")
    private String commentDescription;
    @ApiModelProperty(value = "评论图片")
    private String commentImageId;
    @ApiModelProperty(value = "评论人")
    private String commentUserId;
    private String id;
    @ApiModelProperty(value = "评论用户基本信息")
    private UserInfo userInfo;
}
