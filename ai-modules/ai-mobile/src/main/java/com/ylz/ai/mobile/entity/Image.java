package com.ylz.ai.mobile.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * @Description: 照片
 * @Author: haifeng.lv
 * @Date: 2020-05-14 09:55
 */
@Data
@TableName("image")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="image对象", description="照片")
public class Image {
    
	/**浏览次数*/
    @ApiModelProperty(value = "浏览次数")
	private Integer browseNumber;
	/**创建时间*/
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建时间")
	private java.time.LocalDateTime crtTime;
	/**描述*/
    @ApiModelProperty(value = "描述")
	private String description;
	/**id*/
	@TableId(type = IdType.UUID)
    @ApiModelProperty(value = "id")
	private String id;
	/**是否启用 0 禁用 1 启用*/
    @ApiModelProperty(value = "是否启用 0 禁用 1 启用")
	private Integer isEnable;
	/**0 不公开 1 公开*/
    @ApiModelProperty(value = "0 不公开 1 公开")
	private Integer isOpen;
	/**点赞数*/
    @ApiModelProperty(value = "点赞数")
	private Integer likeNumber;
	/**name*/
    @ApiModelProperty(value = "name")
	private String name;
	/**处理状态 0 未处理 1 已处理 2 失败*/
    @ApiModelProperty(value = "处理状态 0 未处理 1 已处理 2 失败")
	private Integer processStatus;
	/**prototypeVisitAddress*/
    @ApiModelProperty(value = "prototypeVisitAddress")
	private String prototypeVisitAddress;
	/**识别类型id*/
    @ApiModelProperty(value = "识别类型id")
	private String recognitionTypeId;
	/**recognitionVisitAddress*/
    @ApiModelProperty(value = "recognitionVisitAddress")
	private String recognitionVisitAddress;
	/**转发次数*/
    @ApiModelProperty(value = "转发次数")
	private Integer redirectNumber;
	/**size*/
    @ApiModelProperty(value = "size")
	private Integer size;
	/**title*/
    @ApiModelProperty(value = "title")
	private String title;
	/**最后更新时间*/
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "最后更新时间")
	private java.time.LocalDateTime updTime;
	/**最后更新人*/
    @ApiModelProperty(value = "最后更新人")
	private String updUser;
	/**front_user表中对应id*/
    @ApiModelProperty(value = "front_user表中对应id")
	private String uploadUserId;
}
