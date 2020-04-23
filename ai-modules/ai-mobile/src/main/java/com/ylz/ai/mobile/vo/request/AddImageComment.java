package com.ylz.ai.mobile.vo.request;

import lombok.Data;

/**
 * @Description 添加照片评论
 * @Author haifeng.lv
 * @Date 2020/4/23 15:18
 */
@Data
public class AddImageComment {
    /**
     * 照片 id
     */
    private String ImageId;
    /**
     * 评论内容
     */
    private String comment;
}
