package com.ssafy.yesrae.common.exception;

import com.ssafy.yesrae.common.model.BaseException;

public class DuplicateEmailException extends BaseException {

    public DuplicateEmailException() {
        super(ErrorCode.DUPLICATED_EMAIL);
    }
}
