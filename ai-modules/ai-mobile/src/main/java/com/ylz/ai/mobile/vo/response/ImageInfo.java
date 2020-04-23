package com.ylz.ai.mobile.vo.response;

import com.ylz.ai.mobile.entity.ImageComment;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;
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
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private LocalDateTime crtTime;
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
     * name
     */
    private String name;
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
    private String uploadUserId;

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
    private List<ImageComment> comments;
}
