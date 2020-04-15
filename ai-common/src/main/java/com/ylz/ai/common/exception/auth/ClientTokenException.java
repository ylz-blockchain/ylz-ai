package com.ylz.ai.common.exception.auth;

import com.ylz.ai.common.constant.RestCodeConstants;
import com.ylz.ai.common.exception.base.BaseException;

public class ClientTokenException extends BaseException {
    public ClientTokenException(String message) {
        super(message, RestCodeConstants.EX_CLIENT_INVALID_CODE);
    }
}