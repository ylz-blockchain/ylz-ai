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
 * @Date: 2020-04-21 14:46
 */
@Data
@TableName("image")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="image对象", description="照片")
public class Image {
    
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
	/**likeNumber*/
    @ApiModelProperty(value = "likeNumber")
	private Integer likeNumber;
	/**name*/
    @ApiModelProperty(value = "name")
	private String name;
	/**prototypeVisitAddress*/
    @ApiModelProperty(value = "prototypeVisitAddress")
	private String prototypeVisitAddress;
	/**recognitionVisitAddress*/
    @ApiModelProperty(value = "recognitionVisitAddress")
	private String recognitionVisitAddress;
	/**size*/
    @ApiModelProperty(value = "size")
	private Integer size;
	/**0 不公开 1 公开*/
    @ApiModelProperty(value = "0 不公开 1 公开")
	private Integer type;
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
