package com.ylz.ai.mobile.vo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Description 照片简略返回
 * @Author haifeng.lv
 * @Date 2020/4/30 14:09
 */
@ApiModel(value="ImageMinInfo", description="ImageMinInfo")
@Data
public class ImageMinInfo {
    @ApiModelProperty(value = "浏览次数")
    private Integer browseNumber;
    @ApiModelProperty(value = "描述")
    private String description;
    @ApiModelProperty(value = "id")
    private String id;
    @ApiModelProperty(value = "0 不公开 1 公开")
    private Integer isOpen;
    @ApiModelProperty(value = "点赞数")
    private Integer likeNumber;
    @ApiModelProperty(value = "name")
    private String name;
    @ApiModelProperty(value = "原图访问地址")
    private String prototypeVisitAddress;
    @ApiModelProperty(value = "识别类型名称")
    private String recognitionTypeName;
    @ApiModelProperty(value = "识别访问地址")
    private String recognitionVisitAddress;
    @ApiModelProperty(value = "转发次数")
    private Integer redirectNumber;
    @ApiModelProperty(value = "size")
    private String size;
    @ApiModelProperty(value = "title")
    private String title;
    @ApiModelProperty(value = "上传用户昵称")
    private String uploadUserName;
    @ApiModelProperty(value = "处理状态 0 未处理 1 已处理 2 失败")
    private Integer processStatus;
}
