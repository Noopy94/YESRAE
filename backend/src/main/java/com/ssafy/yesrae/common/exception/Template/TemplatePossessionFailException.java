package com.ssafy.yesrae.common.exception.Template;

import com.ssafy.yesrae.common.exception.ErrorCode;
import com.ssafy.yesrae.common.model.BaseException;

public class TemplatePossessionFailException extends BaseException {

    public TemplatePossessionFailException() {
        super(ErrorCode.OUT_OF_POSSESSION);
    }
}
