package com.ylz.ai.mobile.constant;

import com.ylz.ai.common.error.ErrCodeBaseConstant;
import com.ylz.ai.common.error.ErrorCode;

/**
 * @Description 错误码
 * @Author haifeng.lv
 * @Date 2020/1/6 10:32
 */
public class ErrCodeConstant extends ErrCodeBaseConstant {
    /**
     * 微信登录失败
     */
    public static final ErrorCode WEIXIN_LOGIN_ERROR = new ErrorCode("30001", "微信登录失败");
    /**
     * 不可重复点赞
     */
    public static final ErrorCode LIKE_REPEAT_ERROR = new ErrorCode("30002", "不可重复点赞");
    /**
     * 没有查询到该用户
     */
    public static final ErrorCode NO_USER_ERROR = new ErrorCode("30003", "没有查询到该用户");
    /**
     * 不可关注自己
     */
    public static final ErrorCode NO_ATTENTION_SELF_ERROR = new ErrorCode("30004", "不可关注自己");
    /**
     * 不可重复关注
     */
    public static final ErrorCode ATTENTION_REPEAT_ERROR = new ErrorCode("30005", "不可重复关注");
    /**
     * 无法获取文件大小
     */
    public static final ErrorCode NO_GET_FILE_SIZE_ERROR = new ErrorCode("30006", "无法获取文件大小");
    /**
     * 获取微信小程序 token失败
     */
    public static final ErrorCode GET_WEIXIN_TOKEN_ERR = new ErrorCode("30007", "获取微信小程序 token失败");
    /**
     * 内容涉嫌敏感
     */
    public static final ErrorCode CONTENT_NO_ALLOW_ERR = new ErrorCode("30008", "内容涉嫌敏感");
}
