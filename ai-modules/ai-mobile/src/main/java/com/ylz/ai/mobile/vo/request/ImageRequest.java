package com.ylz.ai.mobile.vo.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Description 照片请求
 * @Author haifeng.lv
 * @Date 2020/4/30 14:05
 */
@Data
@ApiModel(value="照片筛选请求", description="照片筛选请求")
public class ImageRequest {
    private String name;
    private String title;
    @ApiModelProperty(value = "识别类型")
    private String recognitionTypeId;
    @ApiModelProperty(value = "是否公开 0 不公开 1 公开")
    private Integer isOpen;
    @ApiModelProperty(value = "处理状态 0 未处理 1 已处理 2 失败 3 审核失败")
    private Integer processStatus;
    @ApiModelProperty(value = "描述")
    private String description;
}
