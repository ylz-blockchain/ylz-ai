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
}
