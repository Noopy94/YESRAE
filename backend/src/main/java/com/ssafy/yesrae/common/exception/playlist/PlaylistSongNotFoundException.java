package com.ssafy.yesrae.common.exception.playlist;

import com.ssafy.yesrae.common.exception.ErrorCode;
import com.ssafy.yesrae.common.model.BaseException;

public class PlaylistSongNotFoundException extends BaseException {

    public PlaylistSongNotFoundException() {
        super(ErrorCode.PlaylistSong_NOT_FOUND);
    }
}
