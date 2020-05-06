package com.ylz.ai.mobile.vo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @Description 照片基础信息
 * @Author haifeng.lv
 * @Date 2020/4/22 10:34
 */
@Data
@ApiModel(value = "imageInfo", description = "imageInfo")
public class ImageInfo {
    @ApiModelProperty(value = "描述")
    private String description;
    private String id;
    @ApiModelProperty(value = "点赞数")
    private Integer likeNumber = 0;
    @ApiModelProperty(value = "0 不公开 1 公开")
    private Integer isOpen;
    @ApiModelProperty(value = "转发次数")
    private Integer redirectNumber = 0;
    @ApiModelProperty(value = "浏览次数")
    private Integer browseNumber = 0;
    private String name;
    private String title;
    @ApiModelProperty(value = "源访问地址")
    private String prototypeVisitAddress;
    @ApiModelProperty(value = "识别访问地址")
    private String recognitionVisitAddress;
    @ApiModelProperty(value = "大小")
    private Integer size = 0;
    private UserInfo userInfo;

    @ApiModelProperty(value = "是否被当前用户点赞 false 未点赞, ture 已点赞")
    private Boolean isLike = false;

    @ApiModelProperty(value = "处理状态 0 未处理 1 已处理")
    private Integer processStatus;

    @ApiModelProperty(value = "上传用户是否被当前用户所关注 false 未关注, ture 已关注")
    private Boolean isAttention = false;

    @ApiModelProperty(value = "照片评论")
    private List<ImageCommentInfo> comments;
}
