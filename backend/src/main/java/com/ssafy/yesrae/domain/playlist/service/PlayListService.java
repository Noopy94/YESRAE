package com.ssafy.yesrae.domain.playlist.service;

import com.ssafy.yesrae.domain.playlist.dto.request.PlayListRegistPostReq;
import com.ssafy.yesrae.domain.playlist.dto.response.PlayListGetResponse;
import com.ssafy.yesrae.domain.playlist.entity.PlayList;
import java.util.List;

public interface PlayListService {

    public void registPlayList(PlayListRegistPostReq registInfo);

    public List<PlayListGetResponse> GetMyPlayList(Long userId);

    public List<PlayListGetResponse> SearchByTitlePlayList(String search);

    public List<PlayListGetResponse> SearchByTagPlayList(String tag);

}
