package com.ssafy.yesrae.common.exception.playlist;

import com.ssafy.yesrae.common.exception.ErrorCode;
import com.ssafy.yesrae.common.model.BaseException;

public class PlaylistTagNotFoundException extends BaseException {

    public PlaylistTagNotFoundException() {
        super(ErrorCode.PlaylistTag_NOT_FOUND);
    }
}
