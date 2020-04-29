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
 * @Description: 前端用户
 * @Author: haifeng.lv
 * @Date: 2020-04-27 10:45
 */
@Data
@TableName("front_user")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="front_user对象", description="前端用户")
public class FrontUser {
    
	/**创建时间*/
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建时间")
	private java.time.LocalDateTime crtTime;
	/**创建人*/
    @ApiModelProperty(value = "创建人")
	private String crtUser;
	/**描述*/
    @ApiModelProperty(value = "描述")
	private String description;
	/**headImage*/
    @ApiModelProperty(value = "headImage")
	private String headImage;
	/**id*/
	@TableId(type = IdType.UUID)
    @ApiModelProperty(value = "id")
	private String id;
	/**ip*/
    @ApiModelProperty(value = "ip")
	private String ip;
	/**lastLoginTime*/
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "lastLoginTime")
	private java.time.LocalDateTime lastLoginTime;
	/**昵称*/
    @ApiModelProperty(value = "昵称")
	private String name;
	/**password*/
    @ApiModelProperty(value = "password")
	private String password;
	/**phoneNumber*/
    @ApiModelProperty(value = "phoneNumber")
	private String phoneNumber;
	/**qqNumber*/
    @ApiModelProperty(value = "qqNumber")
	private String qqNumber;
	/**0 男 1 女*/
    @ApiModelProperty(value = "0 男 1 女")
	private Integer sex;
	/**最后更新时间*/
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "最后更新时间")
	private java.time.LocalDateTime updTime;
	/**最后更新人*/
    @ApiModelProperty(value = "最后更新人")
	private String updUser;
	/**登录名*/
    @ApiModelProperty(value = "登录名")
	private String username;
	/**wxNumber*/
    @ApiModelProperty(value = "wxNumber")
	private String wxNumber;
}
