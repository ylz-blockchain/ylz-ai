package com.ylz.ai.mobile.rabbitmq.vo;

import lombok.Data;

/**
 * @Description 照片识别返回
 * @Author haifeng.lv
 * @Date 2020/5/6 11:25
 */
@Data
public class ImageAiResponse {
    private String id;
    /**
     * 识别后的图访问地址
     */
    private String recognitionVisitAddress;
    /**
     * 返回状态情况
     * 0 失败
     * 1 成功
     */
    private Integer status;
}
