package com.ylz.ai.mobile.vo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Description 照片状态返回
 * @Author haifeng.lv
 * @Date 2020/5/6 11:05
 */
@Data
@ApiModel(value="ImageStatusInfo", description="ImageStatusInfo")
public class ImageStatusInfo {
    @ApiModelProperty(value = "处理状态 0 未处理 1 已处理")
    private Integer status;
    @ApiModelProperty(value = "识别访问地址 未处理完成时会为空")
    private String recognitionVisitAddress;
}
