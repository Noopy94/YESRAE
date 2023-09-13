package com.ssafy.yesrae.domain.playlist.service;

import com.ssafy.yesrae.domain.playlist.dto.request.PlayListDeletePutReq;
import com.ssafy.yesrae.domain.playlist.dto.request.PlayListImgModifyPutReq;
import com.ssafy.yesrae.domain.playlist.dto.request.PlayListLikeDeletePutReq;
import com.ssafy.yesrae.domain.playlist.dto.request.PlayListLikeRegistPostReq;
import com.ssafy.yesrae.domain.playlist.dto.request.PlayListModifyPutReq;
import com.ssafy.yesrae.domain.playlist.dto.request.PlayListRegistPostReq;
import com.ssafy.yesrae.domain.playlist.dto.request.PlayListSongDeletePutReq;
import com.ssafy.yesrae.domain.playlist.dto.request.PlayListSongRegistPostReq;
import com.ssafy.yesrae.domain.playlist.dto.request.PlayListTagDeletePutReq;
import com.ssafy.yesrae.domain.playlist.dto.request.PlayListTagRegistPostReq;
import com.ssafy.yesrae.domain.playlist.dto.response.PlayListGetResponse;
import com.ssafy.yesrae.domain.playlist.entity.PlayList;
import com.ssafy.yesrae.domain.playlist.entity.PlayListLike;
import com.ssafy.yesrae.domain.playlist.entity.PlayListSong;
import com.ssafy.yesrae.domain.playlist.entity.PlayListTag;
import com.ssafy.yesrae.domain.song.entity.Song;
import java.io.IOException;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

public interface PlayListService {

    PlayList registPlayList(PlayListRegistPostReq registInfo, MultipartFile img) throws IOException;

    void modifyPlayList(PlayListModifyPutReq modifyInfo);

    void modifyPlayListImg(PlayListImgModifyPutReq modifyInfo) throws IOException;

    void deletePlayList(PlayListDeletePutReq deleteInfo);

    PlayListTag registPlayListTag(PlayListTagRegistPostReq registInfo);

    void deletePlayListTag(PlayListTagDeletePutReq deleteInfo);

    // 플레이 리스트에 노래 추가
    PlayListSong registSongInPlayList(PlayListSongRegistPostReq registInfo);

    // 플레이 리스트 속 노래 삭제
    void deletePlayListSong(PlayListSongDeletePutReq deleteInfo);

    void registPlayListLike(PlayListLikeRegistPostReq registInfo);

    /// 플레이 리스트 좋아요 삭제
    void deletePlayListLike(PlayListLikeDeletePutReq deleteInfo);

    // 플레이 리스트 안에 등록된 노래 조회
    List<Song> getPlayListSong(Long playListId);

    // 비로그인시 플레이 리스트 홈화면 추천 플레이 리스트(7가지)
    List<PlayList> getHomeRecommendPlayList();

    // 유저 로그인시 플레이 리스트 홈화면 추천 플레이 리스트(7가지)
    List<PlayList> getHomeRecommendPlayList(Long userId);

    //유저 로그인시 팔로워의 추천 플레이 리스트 (7가지)
    List<PlayList> getFollowerPlayList(Long userId);

    // 자기 플레이 리스트 가져오기
    List<PlayList> getUserPlayList(Long userId);

    // 제목으로 플레이 리스트 검색, 페이지네이션 필요
    Page<PlayListGetResponse> searchByTitlePlayList(String keyword, Pageable pageable);

    // 태그로 플레이 리스트 검색, 페이지네이션 필요
    Page<PlayListGetResponse> searchByTagPlayList(String keyword, Pageable pageable);

    // 플레이 리스트 좋아요 수 세기
    Long countPlayListLike(Long playListId);

}
