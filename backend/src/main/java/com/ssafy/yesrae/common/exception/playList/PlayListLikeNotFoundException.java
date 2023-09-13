package com.ssafy.yesrae.common.exception.playList;

import com.ssafy.yesrae.common.exception.ErrorCode;
import com.ssafy.yesrae.common.model.BaseException;

public class PlayListLikeNotFoundException extends BaseException {

    public PlayListLikeNotFoundException() {
        super(ErrorCode.PlayListLike_NOT_FOUND);
    }
}
