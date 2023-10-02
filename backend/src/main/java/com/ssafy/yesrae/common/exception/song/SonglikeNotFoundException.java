package com.ssafy.yesrae.common.exception.song;

import com.ssafy.yesrae.common.exception.ErrorCode;
import com.ssafy.yesrae.common.model.BaseException;

public class SonglikeNotFoundException extends BaseException {

    public SonglikeNotFoundException() {
        super(ErrorCode.Songlike_NOT_FOUND);
    }

}