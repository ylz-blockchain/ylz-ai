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
    /**
     * 描述
     */
    private String description;
    /**
     * id
     */
    private String id;
    /**
     * likeNumber
     */
    @ApiModelProperty(value = "点赞数")
    private Integer likeNumber;
    /**
     * 转发次数
     */
    @ApiModelProperty(value = "转发次数")
    private Integer redirectNumber;
    /**
     * 浏览次数
     */
    @ApiModelProperty(value = "浏览次数")
    private Integer browseNumber;
    /**
     * name
     */
    private String name;
    /**
     * title
     */
    private String title;
    /**
     * prototypeVisitAddress
     */
    @ApiModelProperty(value = "源访问地址")
    private String prototypeVisitAddress;
    /**
     * recognitionVisitAddress
     */
    @ApiModelProperty(value = "识别访问地址")
    private String recognitionVisitAddress;
    /**
     * size
     */
    @ApiModelProperty(value = "大小")
    private Integer size;
    private UserInfo userInfo;

    /**
     * 是否被当前用户点赞
     */
    @ApiModelProperty(value = "是否被当前用户点赞 false 未点赞, ture 已点赞")
    private Boolean isLike;

    /**
     * 上传用户是否被当前用户所关注
     */
    @ApiModelProperty(value = "上传用户是否被当前用户所关注 false 未关注, ture 已关注")
    private Boolean isAttention;

    /**
     * 照片评论
     */
    @ApiModelProperty(value = "照片评论")
    private List<ImageCommentInfo> comments;
}
