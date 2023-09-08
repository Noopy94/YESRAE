package com.ssafy.yesrae.domain.playlist.service;

import com.ssafy.yesrae.domain.playlist.dto.request.PlayListModifyPutReq;
import com.ssafy.yesrae.domain.playlist.dto.request.PlayListRegistPostReq;
import com.ssafy.yesrae.domain.playlist.dto.request.PlayListTitleModifyPutReq;
import com.ssafy.yesrae.domain.playlist.dto.response.PlayListGetResponse;
import com.ssafy.yesrae.domain.playlist.entity.PlayList;
import com.ssafy.yesrae.domain.playlist.entity.PlayListSong;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public interface PlayListService {

    //플레이 리스트 등록
    public void registPlayList(PlayListRegistPostReq registInfo);

    //플레이 리스트 전체 수정(필요한가?)
    public void modifyPlayList(PlayListModifyPutReq modifyInfo);

    //플레이 리스트 태그 삭제
    public void deletePlayListTag(Long tagId);

    //플레이 리스트 태그 추가
    public void addPlayListTag(Long playListId, String tagName);

    //플레이 리스트 하나 전체 삭제
    public void deletePlayList(Long playListId);

    // 플레이 리스트 속 노래 조회
    public List<PlayListSong> getPlayListSong(Long playListId);

    // 플레이 리스트 속 노래 추가
    public void addSongPlayList(Long PlayListId, Long songId);

    // 플레이 리스트 속 노래 삭제
    public void deleteSongPlayList(Long PlayListId, Long songId);

    // 플레이 리스트 홈화면 추천 플레이 리스트
    public List<PlayListGetResponse> getHomeRecommendPlayList();

    //플레이 리스트 홈화면 팔로워들의 플레이 리스트,
    public List<PlayListGetResponse> getFollowerPlayList();

    // 유저에 맞춤형 추천 플레이 리스트
    public List<PlayListGetResponse> getHomeRecommendPlayList(Long userId);

    // 자기 플레이 리스트 가져오기
    public List<PlayListGetResponse> getMyPlayList(Long userId);

    // 제목으로 플레이 리스트 검색
    public List<PlayListGetResponse> searchByTitlePlayList(String searchTitle);

    // 태그로 플레이 리스트 검색
    public List<PlayListGetResponse> searchByTagPlayList(String searchTag);

}
