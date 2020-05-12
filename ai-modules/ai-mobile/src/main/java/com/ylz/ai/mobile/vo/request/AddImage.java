package com.ylz.ai.mobile.vo.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Description 添加照片
 * @Author haifeng.lv
 * @Date 2020/4/30 17:20
 */
@Data
@ApiModel(value="添加照片", description="添加照片")
public class AddImage {
    @ApiModelProperty(value = "名称")
    private String name;
    @ApiModelProperty(value = "标题")
    private String title;
    @ApiModelProperty(value = "原图地址")
    private String prototypeVisitAddress;
    @ApiModelProperty(value = "是否公开 0 不公开 1 公开")
    private Integer isOpen;
    @ApiModelProperty(value = "识别类型id")
    private String recognitionTypeId;
}
