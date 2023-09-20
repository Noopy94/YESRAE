package com.ssafy.yesrae.common.exception.playList;

import com.ssafy.yesrae.common.exception.ErrorCode;
import com.ssafy.yesrae.common.model.BaseException;

public class PlayListSongNotFoundException extends BaseException {

    public PlayListSongNotFoundException() {
        super(ErrorCode.PlayListSong_NOT_FOUND);
    }
}
