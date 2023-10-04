package com.ssafy.yesrae.common.exception.song;

import com.ssafy.yesrae.common.exception.ErrorCode;
import com.ssafy.yesrae.common.model.BaseException;

public class SongNotFoundException extends BaseException {

    public SongNotFoundException() {
        super(ErrorCode.Song_NOT_FOUND);
    }

}