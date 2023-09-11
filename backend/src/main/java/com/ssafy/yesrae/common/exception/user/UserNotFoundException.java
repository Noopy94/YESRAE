package com.ssafy.yesrae.common.exception.user;

import com.ssafy.yesrae.common.exception.ErrorCode;
import com.ssafy.yesrae.common.model.BaseException;

public class UserNotFoundException extends BaseException {

    public UserNotFoundException() {
        super(ErrorCode.USER_NOT_FOUND);
    }
}
