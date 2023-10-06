package com.ssafy.yesrae.common.exception.comment;

import com.ssafy.yesrae.common.exception.ErrorCode;
import com.ssafy.yesrae.common.model.BaseException;

public class CommentPossessionFailException extends BaseException {

    public CommentPossessionFailException() {
        super(ErrorCode.OUT_OF_POSSESSION);
    }
}
