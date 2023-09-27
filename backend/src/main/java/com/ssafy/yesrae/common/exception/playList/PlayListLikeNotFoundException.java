package com.ssafy.yesrae.common.exception.playlist;

import com.ssafy.yesrae.common.exception.ErrorCode;
import com.ssafy.yesrae.common.model.BaseException;

public class PlaylistLikeNotFoundException extends BaseException {

    public PlaylistLikeNotFoundException() {
        super(ErrorCode.PlaylistLike_NOT_FOUND);
    }
}
