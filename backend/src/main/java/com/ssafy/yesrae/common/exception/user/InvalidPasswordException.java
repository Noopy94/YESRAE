package com.ssafy.yesrae.common.exception.user;

import com.ssafy.yesrae.common.exception.ErrorCode;
import com.ssafy.yesrae.common.model.BaseException;

public class InvalidPasswordException extends BaseException {

    public InvalidPasswordException() {
        super(ErrorCode.PASSWORD_NOT_MATCHED);
    }
}
