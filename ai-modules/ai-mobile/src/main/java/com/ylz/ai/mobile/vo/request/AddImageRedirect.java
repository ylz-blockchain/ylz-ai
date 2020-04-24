package com.ylz.ai.mobile.vo.request;

import lombok.Data;

/**
 * @Description 添加照片转发
 * @Author haifeng.lv
 * @Date 2020/4/24 16:08
 */
@Data
public class AddImageRedirect {
    /**
     * 图像 id
     */
    private String imageId;
    /**
     * 转发目标地
     */
    private String redirectPlace;
    /**
     * 描述
     */
    private String description;
}
