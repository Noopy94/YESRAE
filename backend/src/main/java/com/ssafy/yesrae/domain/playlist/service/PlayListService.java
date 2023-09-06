package com.ssafy.yesrae.domain.playlist.service;

import com.ssafy.yesrae.domain.playlist.dto.request.PlayListRegistPostReq;
import com.ssafy.yesrae.domain.playlist.entity.PlayList;

public interface PlayListService {

    public void registPlayList(PlayListRegistPostReq registInfo);


}
