package com.ylz.ai.mobile.annotation;

import com.ylz.ai.mobile.valid.ParamValidatorClass;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * @Description 自定义验证信息
 * @Author haifeng.lv
 * @Date 2020/5/14 10:11
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER,ElementType.FIELD})
@Constraint(validatedBy = ParamValidatorClass.class)
public @interface ParamValidator {
    /**
     * 有效值数组
     */
    int[] values();

    String message() default "参数错误";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
