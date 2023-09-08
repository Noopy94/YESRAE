package com.ssafy.yesrae.domain.playlist.service;

import com.ssafy.yesrae.domain.playlist.dto.request.PlayListModifyPutReq;
import com.ssafy.yesrae.domain.playlist.dto.request.PlayListRegistPostReq;
import com.ssafy.yesrae.domain.playlist.dto.request.PlayListTitleModifyPutReq;
import com.ssafy.yesrae.domain.playlist.dto.response.PlayListGetResponse;
import com.ssafy.yesrae.domain.playlist.entity.PlayListSong;
import com.ssafy.yesrae.domain.playlist.repository.PlayListLikeRepository;
import com.ssafy.yesrae.domain.playlist.repository.PlayListRepository;
import com.ssafy.yesrae.domain.playlist.repository.PlayListSongRepository;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;


@Slf4j
@Transactional
@Service
public class PlayListServiceImpl implements PlayListService{

    private final PlayListLikeRepository playListLikeRepository;
    private final PlayListRepository playListRepository;
    private final PlayListSongRepository playListSongRepository;
    @Autowired
    public PlayListServiceImpl(PlayListLikeRepository playListLikeRepository,
        PlayListRepository playListRepository, PlayListSongRepository playListSongRepository) {
        this.playListLikeRepository = playListLikeRepository;
        this.playListRepository = playListRepository;
        this.playListSongRepository = playListSongRepository;
    }

    @Override
    public void registPlayList(PlayListRegistPostReq registInfo) {

    }

    @Override
    public void modifyPlayList(PlayListModifyPutReq modifyInfo) {

    }
    @Override
    public void deletePlayListTag(Long tagId) {

    }

    @Override
    public void addPlayListTag(Long playListId, String tagName) {

    }

    @Override
    public void deletePlayList(Long playListId) {

    }

    @Override
    public List<PlayListSong> getPlayListSong(Long playListId) {
        return null;
    }

    @Override
    public void addSongPlayList(Long PlayListId, Long songId) {

    }

    @Override
    public void deleteSongPlayList(Long PlayListId, Long songId) {

    }

    @Override
    public List<PlayListGetResponse> getHomeRecommendPlayList() {
        return null;
    }

    @Override
    public List<PlayListGetResponse> getFollowerPlayList() {
        return null;
    }

    @Override
    public List<PlayListGetResponse> getHomeRecommendPlayList(Long userId) {
        return null;
    }

    @Override
    public List<PlayListGetResponse> getMyPlayList(Long userId) {
        return null;
    }

    @Override
    public List<PlayListGetResponse> searchByTitlePlayList(String searchTitle) {
        return null;
    }

    @Override
    public List<PlayListGetResponse> searchByTagPlayList(String searchTag) {
        return null;
    }
}
