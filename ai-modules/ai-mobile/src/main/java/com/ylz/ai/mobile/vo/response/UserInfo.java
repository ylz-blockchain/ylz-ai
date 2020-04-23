package com.ylz.ai.mobile.vo.response;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Description 用户基本信息
 * @Author haifeng.lv
 * @Date 2020/4/23 10:00
 */
@Data
public class UserInfo {
    /**描述*/
    private String description;
    /**headImage*/
    private String headImage;
    /**id*/
    private String id;
    /**ip*/
    private String ip;
    /**lastLoginTime*/
    private LocalDateTime lastLoginTime;
    /**姓名(可以是微信号)*/
    private String name;
    /**phoneNumber*/
    private String phoneNumber;
    /**sex*/
    private Integer sex;
    /**wxNumber*/
    private String wxNumber;
}
