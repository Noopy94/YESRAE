package com.ssafy.yesrae.common.exception.playList;

import com.ssafy.yesrae.common.exception.ErrorCode;
import com.ssafy.yesrae.common.model.BaseException;

public class PlayListNotFoundException extends BaseException {

    public PlayListNotFoundException() {
        super(ErrorCode.PlayList_NOT_FOUND);
    }
}
