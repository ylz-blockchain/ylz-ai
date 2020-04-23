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
    /**commentDescription*/
    @ApiModelProperty(value = "评论内容")
    private String commentDescription;
    /**commentImageId*/
    @ApiModelProperty(value = "评论图片")
    private String commentImageId;
    /**commentUserId*/
    @ApiModelProperty(value = "评论人")
    private String commentUserId;
    /**id*/
    private String id;
}
