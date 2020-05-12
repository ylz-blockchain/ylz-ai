package com.ylz.ai.mobile.rabbitmq.vo;

import lombok.Data;

/**
 * @Description 照片识别请求
 * @Author haifeng.lv
 * @Date 2020/5/6 11:22
 */
@Data
public class ImageAiRequest {
    private String id;
    /**
     * 原图访问地址
     */
    private String prototypeVisitAddress;
}
