package com.ylz.ai.mobile.valid;

import com.ylz.ai.mobile.annotation.ParamValidator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @Description 自定义验证返回结果
 * @Author haifeng.lv
 * @Date 2020/5/14 10:14
 */
public class ParamValidatorClass implements ConstraintValidator<ParamValidator, Object> {
    private int[] values;

    @Override
    public void initialize(ParamValidator constraintAnnotation) {
        this.values = constraintAnnotation.values();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext constraintValidatorContext) {
        if (null == value || !(value instanceof Integer)) {
            return false;
        }

        Integer data = (Integer) value;
        for (int i : values) {
            if (data == i) {
                return true;
            }
        }

        return false;
    }
}
