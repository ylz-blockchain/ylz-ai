package com.ylz.ai.common.exception.auth;

import com.ylz.ai.common.constant.RestCodeConstants;
import com.ylz.ai.common.exception.base.BaseException;

public class NonLoginException extends BaseException {
    public NonLoginException(String message) {
        super(message, RestCodeConstants.EX_USER_INVALID_CODE);
    }
}