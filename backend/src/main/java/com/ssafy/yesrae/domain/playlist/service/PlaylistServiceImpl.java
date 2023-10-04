package com.ssafy.yesrae.domain.playlist.service;


import com.ssafy.yesrae.common.exception.playlist.PlaylistNotFoundException;
import com.ssafy.yesrae.common.exception.playlist.PlaylistTagNotFoundException;
import com.ssafy.yesrae.common.exception.song.SongNotFoundException;
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
import com.ssafy.yesrae.domain.playlist.dto.request.PlaylistTagRegistPostReq;
import com.ssafy.yesrae.domain.playlist.dto.request.PlaylistTagUpdatePutReq;
import com.ssafy.yesrae.domain.playlist.dto.response.PlaylistGetResponse;
import com.ssafy.yesrae.domain.playlist.dto.response.SongGetRes;
import com.ssafy.yesrae.domain.playlist.entity.Playlist;
import com.ssafy.yesrae.domain.playlist.entity.PlaylistLike;
import com.ssafy.yesrae.domain.playlist.entity.PlaylistSong;
import com.ssafy.yesrae.domain.playlist.entity.PlaylistTag;
import com.ssafy.yesrae.domain.playlist.repository.PlaylistLikeRepository;
import com.ssafy.yesrae.domain.playlist.repository.PlaylistRepository;
import com.ssafy.yesrae.domain.playlist.repository.PlaylistSongRepository;
import com.ssafy.yesrae.domain.playlist.repository.PlaylistTagRepository;
import com.ssafy.yesrae.domain.song.entity.Song;
import com.ssafy.yesrae.domain.song.repository.SongRepository;
import com.ssafy.yesrae.domain.user.entity.User;
import com.ssafy.yesrae.domain.user.entity.UserFollow;
import com.ssafy.yesrae.domain.user.repository.UserFollowRepository;
import com.ssafy.yesrae.domain.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
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
    private final SongRepository songRepository;
    private final UserFollowRepository userFollowRepository;


    @Autowired
    public PlaylistServiceImpl(PlaylistLikeRepository playlistLikeRepository,
        PlaylistRepository playlistRepository, PlaylistSongRepository playlistSongRepository,
        PlaylistTagRepository playlistTagRepository, UserRepository userRepository,
        S3Uploader s3Uploader, SongRepository songRepository,
        UserFollowRepository userFollowRepository) {
        this.playlistLikeRepository = playlistLikeRepository;
        this.playlistRepository = playlistRepository;
        this.playlistSongRepository = playlistSongRepository;
        this.playlistTagRepository = playlistTagRepository;
        this.userRepository = userRepository;
        this.s3Uploader = s3Uploader;
        this.songRepository = songRepository;
        this.userFollowRepository = userFollowRepository;
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
            String imgUrl = s3Uploader.upload(img, "playlistId" + playlist.getId());
            playlist.setImgUrl(imgUrl);
        }

        playlistRepository.save(playlist);
        log.info("PlaylistService_PlaylistRegist_end : " + playlist.toString());
        return playlist;

    }

    //Playlist 조회
    public PlaylistGetResponse findPlaylist(Long Id) {

        Playlist playlist = playlistRepository.findById(Id)
            .orElseThrow(PlaylistNotFoundException::new);

        int ispublic = playlist.getIsPublic();

        if (ispublic == 0) {
            return null;
        }

        Long id = playlist.getId();
        String title = playlist.getTitle();
        Long userid = playlist.getUser().getId();
        String description = playlist.getDescription();
        String imgurl = playlist.getImgUrl();
        Long viewcnt = playlist.getViewCount();
        Long likecnt = playlist.getLikeCount();

        PlaylistGetResponse playlistGetResponse = PlaylistGetResponse.builder().
            id(id).
            title(title).
            userId(userid).
            description(description).
            imgUrl(imgurl).
            likeCount(likecnt).
            viewCount(viewcnt).build();

        log.info("PlaylistService_PlaylistRegist_end : " + playlist.toString());
        return playlistGetResponse;

    }


    //PlayList만 수정
    @Override
    public boolean modifyPlaylist(PlaylistModifyPutReq modifyInfo) {

        try {
            log.info("PlaylistService_PlaylistModify_start : " + modifyInfo.toString());

            Playlist playlist = playlistRepository.findById(modifyInfo.getPlaylistId())
                .orElseThrow(PlaylistNotFoundException::new);
            playlist.modifyPlaylist(modifyInfo);

            log.info("PlaylistService_PlaylistModify_end : " + playlist.toString());
            return true;
        } catch (PlaylistNotFoundException e) {
            log.error("PlaylistNotFoundException occurred: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean modifyPlaylistImg(PlaylistImgModifyPutReq modifyInfo) throws IOException {
        //이미지 변경 로직, 이미지 저장 하면서, 새로운 url 제공

        try {
            log.info("PlaylistService_PlaylistImgModify_start : " + modifyInfo.toString());

            Playlist playlist = playlistRepository.findById(modifyInfo.getPlaylistId())
                .orElseThrow(PlaylistNotFoundException::new);
            MultipartFile file = modifyInfo.getFile();

            if (file != null) {
                String imgUrl = s3Uploader.upload(file, "playlistId" + modifyInfo.getPlaylistId());
                playlist.setImgUrl(imgUrl);
            }
            log.info("PlaylistService_PlaylistImgModify_end : " + playlist.toString());
            return true;
        } catch (PlaylistNotFoundException e) {
            log.error("PlaylistNotFoundException occurred: " + e.getMessage());
            return false;
        }

    }

    @Override
    public boolean deletePlaylist(PlaylistDeletePutReq deleteInfo) {

        try {
            log.info("PlaylistService_PlaylistDelete_start : " + deleteInfo.toString());

            Playlist playlist = playlistRepository.findById(deleteInfo.getPlaylistId())
                .orElseThrow(PlaylistNotFoundException::new);
            playlist.deletePlaylist();

            log.info("PlaylistService_PlaylistDelete_end : " + playlist.toString());
            return true;
        } catch (PlaylistNotFoundException e) {
            return false;
        }

    }

    @Override
    public List<PlaylistGetResponse> getBest20LikeCntPlaylist() {

        List<Playlist> lists = playlistRepository.findTop20ByOrderByLikeCountDesc();
        List<PlaylistGetResponse> results = new ArrayList<>();
        for (Playlist playlist : lists) {
            PlaylistGetResponse response = new PlaylistGetResponse(
                playlist.getId(),
                playlist.getUser().getId(),
                playlist.getTitle(),
                playlist.getDescription(),
                playlist.getViewCount(),
                playlist.getLikeCount(),
                playlist.getImgUrl(),
                playlistTagRepository.findTagNameByPlaylist(playlist)
            );
            results.add(response);
        }

        return results;
    }

    @Override
    public List<PlaylistGetResponse> getBest20ViewCntPlaylist() {

        List<Playlist> lists = playlistRepository.findTop20ByOrderByViewCountDesc();
        List<PlaylistGetResponse> results = new ArrayList<>();

        for (Playlist playlist : lists) {
            PlaylistGetResponse response = new PlaylistGetResponse(
                playlist.getId(),
                playlist.getUser().getId(),
                playlist.getTitle(),
                playlist.getDescription(),
                playlist.getViewCount(),
                playlist.getLikeCount(),
                playlist.getImgUrl(),
                playlistTagRepository.findTagNameByPlaylist(playlist)
            );
            results.add(response);
        }

        return results;
    }

    @Override
    public List<PlaylistGetResponse> getUserPlaylist(Long userId) {

        List<Playlist> lists = playlistRepository.findByUser(
            userRepository.findById(userId).orElseThrow(UserNotFoundException::new));
        List<PlaylistGetResponse> results = new ArrayList<>();

        for (Playlist playlist : lists) {
            PlaylistGetResponse response = new PlaylistGetResponse(
                playlist.getId(),
                playlist.getUser().getId(),
                playlist.getTitle(),
                playlist.getDescription(),
                playlist.getViewCount(),
                playlist.getLikeCount(),
                playlist.getImgUrl(),
                playlistTagRepository.findTagNameByPlaylist(playlist)
            );
            results.add(response);
        }

        return results;

    }

    // 유저별 좋아요 표시한 플레이 리스트 가져오기
    @Override
    public List<PlaylistGetResponse> getUserLikePlaylist(User user) {

        log.info("PlaylistService_UserLikePlaylist_start : " + user.toString());

        List<PlaylistLike> playlists = playlistLikeRepository.findAllByUser(user);
        List<PlaylistGetResponse> result = new ArrayList<>();

        for (PlaylistLike c : playlists) {

            Playlist playlist = c.getPlaylist();

            PlaylistGetResponse response = new PlaylistGetResponse(
                playlist.getId(),
                playlist.getUser().getId(),
                playlist.getTitle(),
                playlist.getDescription(),
                playlist.getViewCount(),
                playlist.getLikeCount(),
                playlist.getImgUrl()
            );

            result.add(response);

        }

        return result;
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
    public boolean updatePlaylistTag(PlaylistTagUpdatePutReq updateInfo) {
        try {
            log.info("PlaylistService_PlaylistTagSongDelete_start : " + updateInfo.toString());

            Playlist playlist = playlistRepository.findById(updateInfo.getPlaylistId())
                .orElseThrow();
            List<PlaylistTag> playlistTags = playlistTagRepository.findByPlaylist(playlist);
            List<String> updatedTags = updateInfo.getTagNames();

            // 기존 태그 중에서 업데이트할 태그를 제외한 태그를 삭제합니다.
            List<PlaylistTag> tagsToRemove = new ArrayList<>();
            for (PlaylistTag tag : playlistTags) {
                if (!updatedTags.contains(tag.getTagName())) {
                    tagsToRemove.add(tag);
                }
            }

            for (PlaylistTag tag : tagsToRemove) {
                tag.deletePlaylistTag();
                playlistTagRepository.save(tag);
            }

            // 업데이트할 태그를 추가합니다.
            for (String tagName : updatedTags) {
                if (!playlistTags.stream().anyMatch(tag -> tag.getTagName().equals(tagName))) {
                    // 기존에 없는 태그라면 추가합니다.
                    PlaylistTag newTag = PlaylistTag.builder()
                        .playlist(playlist)
                        .tagName(tagName)
                        .build();
                    playlistTagRepository.save(newTag);
                }
            }

            log.info("PlaylistService_PlaylistTagSongDelete_end : " + updateInfo.toString());
            return true;
        } catch (PlaylistTagNotFoundException e) {
            return false;
        }
    }

    @Override
    public PlaylistSong registSongInPlaylist(PlaylistSongRegistPostReq registInfo) {

        log.info("PlaylistService_PlaylistSongRegist_start : " + registInfo.toString());

        Song song = songRepository.findById(registInfo.getSongId())
            .orElseThrow(SongNotFoundException::new);

        Playlist playlist = playlistRepository.findById(registInfo.getPlaylistId())
            .orElseThrow(PlaylistNotFoundException::new);

        PlaylistSong playlistSong = PlaylistSong.builder()
            .playlist(playlist)
            .song(song)
            .build();

        PlaylistSong playlistSong2 = playlistSongRepository.findBySongAndPlaylist(song, playlist);
        if (playlistSong2 == null) {
            playlistSongRepository.save(playlistSong);
            log.info("PlaylistService_PlaylistSongRegist_end : " + playlistSong.toString());
            return playlistSong;
        } else {
            if (playlistSong2.getDeletedAt() != null) {
                playlistSong2.setDeletedAt();
            }
            playlistSongRepository.save(playlistSong2);
            return playlistSong2;
        }

    }

    @Override
    public boolean deletePlaylistSong(PlaylistSongDeletePutReq deleteInfo) {

        log.info("PlaylistService_PlaylistSongDelete_start : " + deleteInfo.toString());

        Song song = songRepository.findById(deleteInfo.getSongId())
            .orElseThrow(SongNotFoundException::new);

        Playlist playlist = playlistRepository.findById(deleteInfo.getPlaylistId())
            .orElseThrow(PlaylistNotFoundException::new);

        PlaylistSong playlistSong = playlistSongRepository.findBySongAndPlaylist(song, playlist);

        if (playlistSong != null) {
            if (playlistSong.getDeletedAt() == null) {
                playlistSong.deletePlaylistSong();
            }
            log.info("PlaylistService_PlaylistSongDelete_end");
            return true;
        } else {
            return false;
        }

    }

    @Override
    public boolean registPlaylistLike(PlaylistLikeRegistPostReq registInfo) {

        try {

            log.info("PlaylistService_PlaylistLikeRegist_start : " + registInfo.toString());

            User user = userRepository.findById(registInfo.getUserId())
                .orElseThrow(UserNotFoundException::new);

            Playlist playlist = playlistRepository.findById(registInfo.getPlaylistId())
                .orElseThrow(PlaylistNotFoundException::new);

            PlaylistLike playlistLike = playlistLikeRepository.findByUserAndPlaylist(user,
                playlist);

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
            return true;
        } catch (PlaylistNotFoundException e) {
            return false;
        }
    }

    @Override
    public boolean deletePlaylistLike(PlaylistLikeDeletePutReq deleteInfo) {

        try {

            log.info("PlaylistService_PlaylistLikeDelete_start : " + deleteInfo.toString());

            User user = userRepository.findById(deleteInfo.getUserId())
                .orElseThrow(UserNotFoundException::new);

            Playlist playlist = playlistRepository.findById(deleteInfo.getPlaylistId())
                .orElseThrow(PlaylistNotFoundException::new);

            PlaylistLike playlistLike = playlistLikeRepository.findByUserAndPlaylist(user,
                playlist);

            if (playlistLike.getDeletedAt() == null) {
                playlistLike.deletePlaylistLike();
                playlist.decrementLikeCount();
            }

            log.info("PlaylistService_PlaylistLikeDelete_end");
            return true;
        } catch (UserNotFoundException e) {
            return false;
        }
    }

    @Override
    public List<SongGetRes> getPlaylistSong(Long playlistId) {
        log.info("PlaylistService_getPlaylistSong_start: " + playlistId);

        Playlist playlist = playlistRepository.findById(playlistId)
            .orElseThrow(PlaylistNotFoundException::new);

        List<SongGetRes> results = playlistSongRepository.findByPlaylist(playlist).stream()
            .filter(playlistSong -> playlistSong.getDeletedAt() == null)
            .map(PlaylistSong::getSong)
            .map(song -> new SongGetRes(
                song.getId(),
                song.getName(),
                song.getArtistName(),
                song.getImgUrl(),
                song.getPreviewUrl()
            ))
            .collect(Collectors.toList());

        log.info("PlaylistService_getPlaylistSong_end");

        return results;
    }


    // 로그인시 홈화면 투데이 추천 플레이리스트 8가지만 가져오기
    @Override
    public List<PlaylistGetResponse> getHomeRecommendPlaylist(Long userId) {
        return null;
    }

    @Override
    public List<PlaylistGetResponse> getFollowerPlaylist(Long userId) {

        log.info("PlaylistService_getFollowerPlaylist_start: " + userId);

        // follow랑 user 필요
//   user Id에 해당하는 List<User> 팔로우 유저들 리스트 가져와서 다시 findByUser로 List<playlist>가져오기
        List<UserFollow> followingUsers = userFollowRepository.findByUser(
            userRepository.findById(userId).orElseThrow());
        List<Playlist> playlists = new ArrayList<>();

        for (UserFollow c : followingUsers) {
            List<Playlist> lists = playlistRepository.findByUser(c.getUser());
            for (Playlist e : lists) {
                if (e.getIsPublic() == 1) {
                    playlists.add(e);
                }
            }
        }
        Collections.shuffle(playlists);

        // 상위 20개의 요소를 선택합니다.
        int numberOfPlaylistsToSelect = Math.min(20, playlists.size());
        List<Playlist> randomPlaylists = playlists.subList(0, numberOfPlaylistsToSelect);
        List<PlaylistGetResponse> results = new ArrayList<>();

        for (Playlist playlist : randomPlaylists) {
            PlaylistGetResponse response = new PlaylistGetResponse(
                playlist.getId(),
                playlist.getUser().getId(),
                playlist.getTitle(),
                playlist.getDescription(),
                playlist.getViewCount(),
                playlist.getLikeCount(),
                playlist.getImgUrl(),
                playlistTagRepository.findTagNameByPlaylist(playlist)
            );
            results.add(response);
        }

        log.info("PlaylistService_getFollowerPlaylist_end: " + followingUsers.toString());

        return results;

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
                .build()
            );

        log.info("PlaylistService_searchByTitlePlaylist_end");
        return playlistGetResponses;
    }

    //태그로 검색
    @Override
    public Page<PlaylistGetResponse> searchByTagPlaylist(String keyword, Pageable pageable) {

        log.info("PlaylistService_searchByTagPlaylist_start: " + keyword + ", "
            + pageable.toString());

        List<PlaylistTag> playlistTags = playlistTagRepository.findByTagName(keyword);
        List<Long> ids = playlistTags.stream()
            .map(playlistTag -> playlistTag.getPlaylist().getId())
            .collect(Collectors.toList());
        Page<PlaylistGetResponse> playlists = playlistRepository.findAllByIdInCustom(ids, pageable);

        log.info("PlaylistService_searchByTagPlaylist_end");
        return playlists;
    }

}
