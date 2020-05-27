package com.ylz.ai.mobile.vo.response;

import com.lvhaifeng.mybatis.annotation.ColumnOriginal;
import com.ylz.ai.mobile.entity.FrontUserColumn;
import com.ylz.ai.mobile.entity.ImageColumn;
import com.ylz.ai.mobile.entity.RecognitionTypeColumn;
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
    @ApiModelProperty(value = "是否启用 0 禁用 1 启用")
    private Integer isEnable;
    @ApiModelProperty(value = "点赞数")
    private Integer likeNumber;
    @ApiModelProperty(value = "name")
    private String name;
    @ApiModelProperty(value = "原图访问地址")
    private String prototypeVisitAddress;
    @ApiModelProperty(value = "识别类型名称")
    @ColumnOriginal(value = RecognitionTypeColumn.NAME)
    private String recognitionTypeName;
    @ApiModelProperty(value = "识别访问地址")
    private String recognitionVisitAddress;
    @ApiModelProperty(value = "转发次数")
    private Integer redirectNumber;
    @ApiModelProperty(value = "大小")
    @ColumnOriginal(value = ImageColumn.SIZE)
    private String size;
    @ApiModelProperty(value = "标题")
    private String title;
    @ApiModelProperty(value = "上传用户昵称")
    @ColumnOriginal(value = FrontUserColumn.NAME)
    private String uploadUserName;
    @ApiModelProperty(value = "处理状态 0 未处理 1 已处理 2 失败")
    private Integer processStatus;
}
