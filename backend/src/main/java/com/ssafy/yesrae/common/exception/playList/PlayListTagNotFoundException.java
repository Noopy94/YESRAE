package com.ssafy.yesrae.common.exception.playList;

import com.ssafy.yesrae.common.exception.ErrorCode;
import com.ssafy.yesrae.common.model.BaseException;

public class PlayListTagNotFoundException extends BaseException {

    public PlayListTagNotFoundException() {
        super(ErrorCode.PlayListTag_NOT_FOUND);
    }
}
