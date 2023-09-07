package com.ssafy.yesrae.common.exception;

import com.ssafy.yesrae.common.model.BaseException;

public class DuplicateNicknameException extends BaseException {

    public DuplicateNicknameException() {
        super(ErrorCode.DUPLICATED_NICKNAME);
    }
}
