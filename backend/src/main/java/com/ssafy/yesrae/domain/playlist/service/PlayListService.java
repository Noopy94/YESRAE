package com.ssafy.yesrae.domain.playlist.service;

import com.ssafy.yesrae.domain.playlist.dto.request.PlayListDeletePutReq;
import com.ssafy.yesrae.domain.playlist.dto.request.PlayListImgModifyPutReq;
import com.ssafy.yesrae.domain.playlist.dto.request.PlayListModifyPutReq;
import com.ssafy.yesrae.domain.playlist.dto.request.PlayListRegistPostReq;
import com.ssafy.yesrae.domain.playlist.dto.request.PlayListSongDeletePutReq;
import com.ssafy.yesrae.domain.playlist.dto.request.PlayListSongRegistPostReq;
import com.ssafy.yesrae.domain.playlist.dto.request.PlayListTagDeletePutReq;
import com.ssafy.yesrae.domain.playlist.dto.request.PlayListTagRegistPostReq;
import com.ssafy.yesrae.domain.playlist.dto.response.PlayListGetResponse;
import com.ssafy.yesrae.domain.playlist.entity.PlayList;
import com.ssafy.yesrae.domain.playlist.entity.PlayListSong;
import com.ssafy.yesrae.domain.playlist.entity.PlayListTag;
import java.util.List;

public interface PlayListService {

    PlayList registPlayList(PlayListRegistPostReq registInfo);
    void modifyPlayList(PlayListModifyPutReq modifyInfo);

    void modifyPlayListImg(PlayListImgModifyPutReq modifyInfo);
    void deletePlayList(PlayListDeletePutReq deleteInfo);
    PlayListTag registPlayListTag(PlayListTagRegistPostReq registInfo);
    void deletePlayListTag(PlayListTagDeletePutReq deleteInfo);

    // 플레이 리스트에 노래 추가
    PlayListSong registSongInPlayList(PlayListSongRegistPostReq registInfo);

    // 플레이 리스트 속 노래 삭제
    boolean deleteSongPlayList(PlayListSongDeletePutReq deleteInfo);

    // 플레이 리스트 안에 등록된 노래 조회
    List<PlayListSong> getPlayListSong(Long playListId);

    // 비로그인시 플레이 리스트 홈화면 추천 플레이 리스트
    List<PlayList> getHomeRecommendPlayList();

    // 유저 로그인시 플레이 리스트 홈화면 추천 플레이 리스트
    List<PlayList> getHomeRecommendPlayList(Long userId);

    //유저 로그인시 팔로워의 추천 플레이 리스트,
    List<PlayList> getFollowerPlayList(Long userId);

    // 자기 플레이 리스트 가져오기
    List<PlayList> getMyPlayList(Long userId);

    // 제목으로 플레이 리스트 검색
    List<PlayList> searchByTitlePlayList(String searchTitle);

    // 태그로 플레이 리스트 검색
    List<PlayList> searchByTagPlayList(String searchTag);

}
