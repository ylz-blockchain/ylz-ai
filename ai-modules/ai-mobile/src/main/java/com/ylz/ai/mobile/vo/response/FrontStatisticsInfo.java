package com.ylz.ai.mobile.vo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Description 用户基本信息统计
 * @Author haifeng.lv
 * @Date 2020/5/12 16:46
 */
@Data
@ApiModel(value = "frontStatisticsInfo", description = "frontStatisticsInfo")
public class FrontStatisticsInfo {
    @ApiModelProperty(value = "喜欢")
    private Integer likes;
    @ApiModelProperty(value = "上传数")
    private Integer uploads;
    @ApiModelProperty(value = "关注数")
    private Integer attentions;
    @ApiModelProperty(value = "被关注数")
    private Integer beAttentions;
}
