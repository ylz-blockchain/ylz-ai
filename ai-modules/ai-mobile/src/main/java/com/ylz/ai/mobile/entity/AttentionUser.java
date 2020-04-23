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
 * @Description: 关注用户
 * @Author: haifeng.lv
 * @Date: 2020-04-21 14:46
 */
@Data
@TableName("attention_user")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="attention_user对象", description="关注用户")
public class AttentionUser {
    
	/**attentionUserId*/
    @ApiModelProperty(value = "attentionUserId")
	private String attentionUserId;
	/**beAttentionUserId*/
    @ApiModelProperty(value = "beAttentionUserId")
	private String beAttentionUserId;
	/**创建时间*/
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建时间")
	private java.time.LocalDateTime crtTime;
	/**创建人*/
    @ApiModelProperty(value = "创建人")
	private String crtUser;
	/**id*/
	@TableId(type = IdType.UUID)
    @ApiModelProperty(value = "id")
	private String id;
	/**最后更新时间*/
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "最后更新时间")
	private java.time.LocalDateTime updTime;
	/**最后更新人*/
    @ApiModelProperty(value = "最后更新人")
	private String updUser;
}
