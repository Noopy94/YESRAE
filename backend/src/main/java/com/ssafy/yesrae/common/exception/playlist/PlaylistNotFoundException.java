package com.ssafy.yesrae.common.exception.playlist;

import com.ssafy.yesrae.common.exception.ErrorCode;
import com.ssafy.yesrae.common.model.BaseException;

public class PlaylistNotFoundException extends BaseException {

    public PlaylistNotFoundException() {
        super(ErrorCode.Playlist_NOT_FOUND);
    }
}
