package com.ssafy.yesrae.common.exception.song;

import com.ssafy.yesrae.common.exception.ErrorCode;
import com.ssafy.yesrae.common.model.BaseException;

public class SonglikePossessionFailException extends BaseException {

    public SonglikePossessionFailException() {
        super(ErrorCode.OUT_OF_POSSESSION);
    }
}
