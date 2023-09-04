package com.ssafy.yesrae.common.exception;

import com.ssafy.yesrae.common.model.BaseException;

public class NoDataException extends BaseException {

    public NoDataException() {
        super(ErrorCode.NO_SUCH_DATA);
    }
}
