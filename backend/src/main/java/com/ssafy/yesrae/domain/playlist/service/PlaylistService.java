package com.ssafy.yesrae.domain.playlist.service;

import com.ssafy.yesrae.domain.playlist.dto.request.PlaylistDeletePutReq;
import com.ssafy.yesrae.domain.playlist.dto.request.PlaylistImgModifyPutReq;
import com.ssafy.yesrae.domain.playlist.dto.request.PlaylistLikeDeletePutReq;
import com.ssafy.yesrae.domain.playlist.dto.request.PlaylistLikeRegistPostReq;
import com.ssafy.yesrae.domain.playlist.dto.request.PlaylistModifyPutReq;
import com.ssafy.yesrae.domain.playlist.dto.request.PlaylistRegistPostReq;
import com.ssafy.yesrae.domain.playlist.dto.request.PlaylistSongDeletePutReq;
import com.ssafy.yesrae.domain.playlist.dto.request.PlaylistSongRegistPostReq;
import com.ssafy.yesrae.domain.playlist.dto.request.PlaylistTagRegistPostReq;
import com.ssafy.yesrae.domain.playlist.dto.request.PlaylistTagUpdatePutReq;
import com.ssafy.yesrae.domain.playlist.dto.response.PlaylistGetResponse;
import com.ssafy.yesrae.domain.playlist.dto.response.SongGetRes;
import com.ssafy.yesrae.domain.playlist.entity.Playlist;
import com.ssafy.yesrae.domain.playlist.entity.PlaylistSong;
import com.ssafy.yesrae.domain.playlist.entity.PlaylistTag;
import com.ssafy.yesrae.domain.user.entity.User;
import java.io.IOException;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

public interface PlaylistService {

    PlaylistGetResponse findPlaylist(Long id);

    Playlist registPlaylist(PlaylistRegistPostReq registInfo, MultipartFile img) throws IOException;

    boolean modifyPlaylist(PlaylistModifyPutReq modifyInfo);

    boolean modifyPlaylistImg(PlaylistImgModifyPutReq modifyInfo) throws IOException;

    boolean deletePlaylist(PlaylistDeletePutReq deleteInfo);

    PlaylistTag registPlaylistTag(PlaylistTagRegistPostReq registInfo);

    boolean updatePlaylistTag(PlaylistTagUpdatePutReq updateInfo);

    // 플레이 리스트에 노래 추가
    PlaylistSong registSongInPlaylist(PlaylistSongRegistPostReq registInfo);

    // 플레이 리스트 속 노래 삭제
    boolean deletePlaylistSong(PlaylistSongDeletePutReq deleteInfo);

    boolean registPlaylistLike(PlaylistLikeRegistPostReq registInfo);

    /// 플레이 리스트 좋아요 삭제
    boolean deletePlaylistLike(PlaylistLikeDeletePutReq deleteInfo);

    // 플레이 리스트의 태그 정보 조회
    List<String> getPlaylistTag(Long playlistId);

    // 플레이 리스트 안에 등록된 노래 조회
    List<SongGetRes> getPlaylistSong(Long playlistId);

    // 좋아요 베스트 20개 플레이리스트 가져오기
    List<PlaylistGetResponse> getBest20LikeCntPlaylist();

    // 조회수 베스트 20개 플레이리스트 가져오기
    List<PlaylistGetResponse> getBest20ViewCntPlaylist();

    // 유저 플레이 리스트 가져오기
    List<PlaylistGetResponse> getUserPlaylist(Long userId);

    // 좋아요 플레이 리스트 가져오기
    List<PlaylistGetResponse> getUserLikePlaylist(User user);

    // 유저 로그인시 플레이 리스트 홈화면 추천 플레이 리스트를 위한 SongId 4가지(4가지)
    List<String> getHomeRecommendSongs(Long userId);

    //유저 로그인시 팔로워의 추천 플레이 리스트 (8가지)
    List<PlaylistGetResponse> getFollowerPlaylist(Long userId);

    // 제목으로 플레이 리스트 검색, 페이지네이션 필요
    Page<PlaylistGetResponse> searchByTitlePlaylist(String keyword, Pageable pageable);

    // 태그로 플레이 리스트 검색, 페이지네이션 필요
    Page<PlaylistGetResponse> searchByTagPlaylist(String keyword, Pageable pageable);

    boolean findByUserLike(PlaylistLikeRegistPostReq playlistLikeRegistPostReq);


}
