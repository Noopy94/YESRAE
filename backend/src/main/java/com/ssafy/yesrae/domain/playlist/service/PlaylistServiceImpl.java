package com.ssafy.yesrae.domain.playlist.service;


import com.ssafy.yesrae.common.exception.playlist.PlaylistNotFoundException;
import com.ssafy.yesrae.common.exception.playlist.PlaylistTagNotFoundException;
import com.ssafy.yesrae.common.exception.user.UserNotFoundException;
import com.ssafy.yesrae.common.util.S3Uploader;
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
import com.ssafy.yesrae.domain.playlist.entity.PlaylistLike;
import com.ssafy.yesrae.domain.playlist.entity.PlaylistSong;
import com.ssafy.yesrae.domain.playlist.entity.PlaylistTag;
import com.ssafy.yesrae.domain.playlist.repository.PlaylistLikeRepository;
import com.ssafy.yesrae.domain.playlist.repository.PlaylistRepository;
import com.ssafy.yesrae.domain.playlist.repository.PlaylistSongRepository;
import com.ssafy.yesrae.domain.playlist.repository.PlaylistTagRepository;
import com.ssafy.yesrae.domain.song.entity.Song;
import com.ssafy.yesrae.domain.user.entity.User;
import com.ssafy.yesrae.domain.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Transactional
@Service
public class PlaylistServiceImpl implements PlaylistService {

    private final PlaylistLikeRepository playlistLikeRepository;
    private final PlaylistRepository playlistRepository;
    private final PlaylistSongRepository playlistSongRepository;
    private final PlaylistTagRepository playlistTagRepository;
    private final UserRepository userRepository;
    private final S3Uploader s3Uploader;

    @Autowired
    public PlaylistServiceImpl(PlaylistLikeRepository playlistLikeRepository,
        PlaylistRepository playlistRepository, PlaylistSongRepository playlistSongRepository,
        PlaylistTagRepository playlistTagRepository, UserRepository userRepository,
        S3Uploader s3Uploader) {
        this.playlistLikeRepository = playlistLikeRepository;
        this.playlistRepository = playlistRepository;
        this.playlistSongRepository = playlistSongRepository;
        this.playlistTagRepository = playlistTagRepository;
        this.userRepository = userRepository;
        this.s3Uploader = s3Uploader;
    }

    // playList 등록
    @Override
    public Playlist registPlaylist(PlaylistRegistPostReq registInfo, MultipartFile img)
        throws IOException {

        log.info("PlaylistService_PlaylistRegist_start : " + registInfo.toString());

        User user = userRepository.findById(registInfo.getUserId())
            .orElseThrow(UserNotFoundException::new);

        String title = registInfo.getTitle();
        String description = registInfo.getDescription();
        Integer isPublic = registInfo.getIsPublic();

        Playlist playlist = Playlist.builder()
            .user(user)
            .isPublic(isPublic)
            .title(title)
            .description(description)
            .build();

        if (img != null) {
            String imgUrl = s3Uploader.upload(img, "playListId" + playlist.getId());
            playlist.setImgUrl(imgUrl);
        }

        playlistRepository.save(playlist);
        log.info("PlaylistService_PlaylistRegist_end : " + playlist.toString());
        return playlist;

    }

    //PlayList만 수정
    @Override
    public void modifyPlaylist(PlaylistModifyPutReq modifyInfo) {

        log.info("PlaylistService_PlaylistModify_start : " + modifyInfo.toString());

        Playlist playlist = playlistRepository.findById(modifyInfo.getPlaylistId())
            .orElseThrow(PlaylistNotFoundException::new);
        playlist.modifyPlaylist(modifyInfo);

        log.info("PlaylistService_PlaylistModify_end : " + playlist.toString());
    }

    @Override
    public void modifyPlaylistImg(PlaylistImgModifyPutReq modifyInfo) throws IOException {
        //이미지 변경 로직, 이미지 저장 하면서, 새로운 url 제공

        log.info("PlaylistService_PlaylistImgModify_start : " + modifyInfo.toString());

        Playlist playlist = playlistRepository.findById(modifyInfo.getPlaylistId())
            .orElseThrow(PlaylistNotFoundException::new);
        MultipartFile img = modifyInfo.getImg();

        if (img != null) {
            String imgUrl = s3Uploader.upload(img, "playlistId" + modifyInfo.getPlaylistId());
            playlist.setImgUrl(imgUrl);
        }
        log.info("PlaylistService_PlaylistImgModify_end : " + playlist.toString());

    }

    @Override
    public void deletePlaylist(PlaylistDeletePutReq deleteInfo) {

        log.info("PlaylistService_PlaylistDelete_start : " + deleteInfo.toString());

        Playlist playlist = playlistRepository.findById(deleteInfo.getPlaylistId())
            .orElseThrow(PlaylistNotFoundException::new);
        playlist.deletePlaylist();

        log.info("PlaylistService_PlaylistDelete_end : " + playlist.toString());

    }

    @Override
    public PlaylistTag registPlaylistTag(PlaylistTagRegistPostReq registInfo) {

        log.info("PlaylistService_PlaylistTagSongRegist_start : " + registInfo.toString());

        Playlist playlist = playlistRepository.findById(registInfo.getPlaylistId())
            .orElseThrow(PlaylistNotFoundException::new);
        String tagName = registInfo.getTagName();

        PlaylistTag playlistTag = PlaylistTag.builder()
            .playlist(playlist)
            .tagName(tagName)
            .build();

        playlistTagRepository.save(playlistTag);

        log.info("PlaylistService_PlaylistTagSongRegist_end : " + playlistTag.toString());

        return playlistTag;
    }

    @Override
    public void deletePlaylistTag(PlaylistTagDeletePutReq deleteInfo) {

        log.info("PlaylistService_PlaylistTagSongDelete_start : " + deleteInfo.toString());

        PlaylistTag playlistTag = playlistTagRepository.findById(deleteInfo.getPlaylistTagId())
            .orElseThrow(PlaylistTagNotFoundException::new);
        playlistTag.deletePlaylistTag();

        log.info("PlaylistService_PlaylistTagSongDelete_end : " + deleteInfo.toString());
    }

    @Override
    public PlaylistSong registSongInPlaylist(PlaylistSongRegistPostReq registInfo) {

        log.info("PlaylistService_PlaylistSongRegist_start : " + registInfo.toString());

//        Song song = songRepository.findById(registInfo.getSongId())
//            .orElseThrow(SongNotFoundException::new);

        Playlist playlist = playlistRepository.findById(registInfo.getPlaylistId())
            .orElseThrow(PlaylistNotFoundException::new);

//        PlaylistSong playlistSong = PlaylistSong.builder()
//            .playlist(playlist)
//            .song(song)
//            .build();

//        PlaylistSong playlistSong = playlistSongRepository.findBySongAndPlaylist(song, playlist);
//        if (playlistSong==null){
//            playlistSongRepository.save(playlistSong);
//        } else {
//            if (playlistSong.getDeletedAt() != null) playlistSong.setDeletedAt();
//            playlistSongRepository.save(playlistSong);
//        }
//        playlistSongRepository.save(playlistSong);
//        return playlistSong;

//        log.info("PlaylistService_PlaylistSongRegist_end : " + playlistSong.toString());

        return null;
    }

    @Override
    public void deletePlaylistSong(PlaylistSongDeletePutReq deleteInfo) {

        log.info("PlaylistService_PlaylistSongDelete_start : " + deleteInfo.toString());

//        Song song = songRepository.findById(deleteInfo.getSongId())
//            .orElseThrow(SongNotFoundException::new);
//
//        Playlist playlist = playlistRepository.findById(deleteInfo.getPlaylistId())
//            .orElseThrow(PlaylistNotFoundException::new);
//
//        PlaylistSong playlistSong = playlistSongRepository.findBySongAndPlaylist(song, playlist)
//            .orElseThrow(PlaylistSongNotFoundException::new);
//
//        if(playlistSong.getDeletedAt()==null){
//            playlistSong.deletePlaylistSong();
//        }

        log.info("PlaylistService_PlaylistSongDelete_end");
    }

    @Override
    public void registPlaylistLike(PlaylistLikeRegistPostReq registInfo) {

        log.info("PlaylistService_PlaylistLikeRegist_start : " + registInfo.toString());

        User user = userRepository.findById(registInfo.getUserId())
            .orElseThrow(UserNotFoundException::new);

        Playlist playlist = playlistRepository.findById(registInfo.getPlaylistId())
            .orElseThrow(PlaylistNotFoundException::new);

        PlaylistLike playlistLike = playlistLikeRepository.findByUserAndPlaylist(user, playlist);

        if (playlistLike == null) {
            playlistLike = PlaylistLike.builder()
                .user(user)
                .playlist(playlist)
                .build();
            playlistLikeRepository.save(playlistLike);
            playlist.incrementLikeCount();
        } else {
            playlistLike.setDeletedAt();
            playlist.incrementLikeCount();
        }

        log.info("PlaylistService_PlaylistLikeRegist_end");
    }

    @Override
    public void deletePlaylistLike(PlaylistLikeDeletePutReq deleteInfo) {

        log.info("PlaylistService_PlaylistLikeDelete_start : " + deleteInfo.toString());

        User user = userRepository.findById(deleteInfo.getUserId())
            .orElseThrow(UserNotFoundException::new);

        Playlist playlist = playlistRepository.findById(deleteInfo.getPlaylistId())
            .orElseThrow(PlaylistNotFoundException::new);

        PlaylistLike playlistLike = playlistLikeRepository.findByUserAndPlaylist(user, playlist);

        if (playlistLike.getDeletedAt() == null) {
            playlistLike.deletePlaylistLike();
            playlist.decrementLikeCount();
        }

        log.info("PlaylistService_PlaylistLikeDelete_end");
    }

    @Override
    public List<Song> getPlaylistSong(Long playlistId) {

        log.info("PlaylistService_getPlaylistSong_start : " + playlistId);

        Playlist playlist = playlistRepository.findById(playlistId)
            .orElseThrow(PlaylistNotFoundException::new);

        List<PlaylistSong> playlistSongs = playlistSongRepository.findByPlaylist(playlist);

        // playlistSongs 배열을 받아서 SongId 확인해서 다시 Song들을 반환할거임;
        //

        log.info("PlaylistService_getPlaylistSong_end");

        return null;
    }

    // 비로그인시 홈화면 투데이 추천 플레이리스트 7가지만 가져오기
    @Override
    public List<Playlist> getHomeRecommendPlaylist() {

        log.info("PlaylistService_getHomeRecommendPlaylist_start");

        // 추천 로직 아직 모름 패스

        log.info("PlaylistService_getHomeRecommendPlaylist_end");

        return null;
    }

    // 로그인시 홈화면 투데이 추천 플레이리스트 7가지만 가져오기
    @Override
    public List<Playlist> getHomeRecommendPlaylist(Long userId) {
        return null;
    }

    @Override
    public List<Playlist> getFollowerPlaylist(Long userId) {

        log.info("PlaylistService_getFollowerPlaylist_start: " + userId);

        // follow랑 user 필요
//   user Id에 해당하는 List<User> 팔로우 유저들 리스트 가져와서 다시 findByUser로 List<playlist>가져오기
        List<User> followingUsers = new ArrayList<>();

        log.info("PlaylistService_getFollowerPlaylist_end: " + followingUsers.toString());
        return null;
    }

    @Override
    public List<Playlist> getUserPlaylist(Long userId) {

        log.info("PlaylistService_getUserPlaylist_start: " + userId);

        User user = userRepository.findById(userId)
            .orElseThrow(UserNotFoundException::new);

        List<Playlist> myPlaylists = playlistRepository.findByUser(user);

        log.info("PlaylistService_getUserPlaylist_start: " + myPlaylists.toString());

        return myPlaylists;
    }

    @Override
    public Page<PlaylistGetResponse> searchByTitlePlaylist(String keyword, Pageable pageable) {

        log.info("PlaylistService_searchByTitlePlaylist_start: " + keyword + ", "
            + pageable.toString());

        Page<PlaylistGetResponse> playlistGetResponses = playlistRepository.findByTitleContainingAndIsPublic(
                keyword, 1, pageable)
            .map(m -> PlaylistGetResponse.builder()
                .id(m.getId())
                .userId(m.getUser().getId())
                .title(m.getTitle())
                .description(m.getDescription())
                .viewCount(m.getViewCount())
                .likeCount(m.getLikeCount())
                .imgUrl(m.getImgUrl())
                .createdAt(m.getCreatedAt())
                .build()
            );

        log.info("PlaylistService_searchByTitlePlaylist_end");
        return playlistGetResponses;
    }

    @Override
    public Page<PlaylistGetResponse> searchByTagPlaylist(String keyword, Pageable pageable) {
        return null;
    }

//    @Override
//    public Page<PlaylistGetResponse> searchByTagPlaylist(String keyword, Pageable pageable){
//
//        log.info("PlaylistService_searchByTagPlaylist_start: " + keyword + ", "
//            + pageable.toString());
//
//        Page<PlaylistGetResponse> playlistGetResponses = playlistRepository.findByTagName_PlayLisTagAndIsPublic(keyword, 1, pageable)
//                .map(m -> PlaylistGetResponse.builder()
//                    .id(m.getId())
//                    .user_id(m.getUser().getId())
//                    .title(m.getTitle())
//                    .description(m.getDescription())
//                    .viewCount(m.getViewCount())
//                    .likeCount(m.getLikeCount())
//                    .imgUrl(m.getImgUrl())
//                    .created_data(m.getCreatedAt())
//                    .build()
//                );
//
//        log.info("PlaylistService_searchByTagPlaylist_end");
//        return playlistGetResponses;
//    }

    @Override
    public Long countPlaylistLike(Long playlistId) {

        log.info("PlaylistService_CountPlaylistLike_start : " + playlistId);

        Long result = playlistLikeRepository.countByPlaylistIdAndDeletedAtIsNull(playlistId);

        log.info("PlaylistService_CountPlaylistLike_end : " + result);
        return result;
    }


}
