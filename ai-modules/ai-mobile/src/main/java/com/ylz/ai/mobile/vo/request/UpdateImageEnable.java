package com.ylz.ai.mobile.vo.request;

import com.ylz.ai.mobile.annotation.ParamValidator;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @Description 修改照片是否启用
 * @Author haifeng.lv
 * @Date 2020/5/14 9:58
 */
@Data
public class UpdateImageEnable {
    @NotNull(message = "图像 id不得为空")
    private String id;
    /**
     * 是否启用
     * 0 禁用
     * 1 启用
     */
    @ParamValidator(values = {0, 1})
    private Integer isEnable;
}
