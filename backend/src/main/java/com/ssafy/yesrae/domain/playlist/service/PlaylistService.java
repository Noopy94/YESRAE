package com.ssafy.yesrae.domain.playlist.service;

import com.ssafy.yesrae.domain.playlist.dto.request.PlaylistDeletePutReq;
import com.ssafy.yesrae.domain.playlist.dto.request.PlaylistImgModifyPutReq;
import com.ssafy.yesrae.domain.playlist.dto.request.PlaylistLikeDeletePutReq;
import com.ssafy.yesrae.domain.playlist.dto.request.PlaylistLikeRegistPostReq;
import com.ssafy.yesrae.domain.playlist.dto.request.PlaylistModifyPutReq;
import com.ssafy.yesrae.domain.playlist.dto.request.PlaylistRegistPostReq;
import com.ssafy.yesrae.domain.playlist.dto.request.PlaylistSongDeletePutReq;
import com.ssafy.yesrae.domain.playlist.dto.request.PlaylistSongRegistPostReq;
import com.ssafy.yesrae.domain.playlist.dto.request.PlaylistTagDeletePutReq;
import com.ssafy.yesrae.domain.playlist.dto.request.PlaylistTagRegistPostReq;
import com.ssafy.yesrae.domain.playlist.dto.response.PlaylistGetResponse;
import com.ssafy.yesrae.domain.playlist.entity.Playlist;
import com.ssafy.yesrae.domain.playlist.entity.PlaylistSong;
import com.ssafy.yesrae.domain.playlist.entity.PlaylistTag;
import com.ssafy.yesrae.domain.song.entity.Song;
import java.io.IOException;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

public interface PlaylistService {

    Playlist registPlaylist(PlaylistRegistPostReq registInfo, MultipartFile img) throws IOException;

    void modifyPlaylist(PlaylistModifyPutReq modifyInfo);

    void modifyPlaylistImg(PlaylistImgModifyPutReq modifyInfo) throws IOException;

    void deletePlaylist(PlaylistDeletePutReq deleteInfo);

    PlaylistTag registPlaylistTag(PlaylistTagRegistPostReq registInfo);

    void deletePlaylistTag(PlaylistTagDeletePutReq deleteInfo);

    // 플레이 리스트에 노래 추가
    PlaylistSong registSongInPlaylist(PlaylistSongRegistPostReq registInfo);

    // 플레이 리스트 속 노래 삭제
    void deletePlaylistSong(PlaylistSongDeletePutReq deleteInfo);

    void registPlaylistLike(PlaylistLikeRegistPostReq registInfo);

    /// 플레이 리스트 좋아요 삭제
    void deletePlaylistLike(PlaylistLikeDeletePutReq deleteInfo);

    // 플레이 리스트 안에 등록된 노래 조회
    List<Song> getPlaylistSong(Long playlistId);

    // 비로그인시 플레이 리스트 홈화면 추천 플레이 리스트(7가지)
    List<Playlist> getHomeRecommendPlaylist();

    // 유저 로그인시 플레이 리스트 홈화면 추천 플레이 리스트(7가지)
    List<Playlist> getHomeRecommendPlaylist(Long userId);

    //유저 로그인시 팔로워의 추천 플레이 리스트 (7가지)
    List<Playlist> getFollowerPlaylist(Long userId);

    // 자기 플레이 리스트 가져오기
    List<Playlist> getUserPlaylist(Long userId);

    // 제목으로 플레이 리스트 검색, 페이지네이션 필요
    Page<PlaylistGetResponse> searchByTitlePlaylist(String keyword, Pageable pageable);

    // 태그로 플레이 리스트 검색, 페이지네이션 필요
    Page<PlaylistGetResponse> searchByTagPlaylist(String keyword, Pageable pageable);

    // 플레이 리스트 좋아요 수 세기
    Long countPlaylistLike(Long playlistId);

}
