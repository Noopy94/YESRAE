package com.ssafy.yesrae.domain.playlist.service;

import com.ssafy.yesrae.domain.playlist.dto.request.PlayListRegistPostReq;
import com.ssafy.yesrae.domain.playlist.dto.response.PlayListGetResponse;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Slf4j
@Transactional
@Service
public class PlayListServiceImpl implements PlayListService{

    @Override
    public void registPlayList(PlayListRegistPostReq registInfo) {

    }

    @Override
    public List<PlayListGetResponse> GetMyPlayList(Long userId) {
        return null;
    }

    @Override
    public List<PlayListGetResponse> SearchByTitlePlayList(String search) {
        return null;
    }

    @Override
    public List<PlayListGetResponse> SearchByTagPlayList(String tag) {
        return null;
    }
}
