package com.ssafy.yesrae.domain.playlist.service;


import com.ssafy.yesrae.common.exception.playList.PlayListLikeNotFoundException;
import com.ssafy.yesrae.common.exception.playList.PlayListNotFoundException;
import com.ssafy.yesrae.common.exception.playList.PlayListTagNotFoundException;
import com.ssafy.yesrae.common.exception.user.UserNotFoundException;
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
import com.ssafy.yesrae.domain.playlist.repository.PlayListLikeRepository;
import com.ssafy.yesrae.domain.playlist.repository.PlayListRepository;
import com.ssafy.yesrae.domain.playlist.repository.PlayListSongRepository;
import com.ssafy.yesrae.domain.playlist.repository.PlayListTagRepository;
import com.ssafy.yesrae.domain.song.entity.Song;
import com.ssafy.yesrae.domain.user.entity.User;
import com.ssafy.yesrae.domain.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@Transactional
@Service
public class PlayListServiceImpl implements PlayListService{

    private final PlayListLikeRepository playListLikeRepository;
    private final PlayListRepository playListRepository;
    private final PlayListSongRepository playListSongRepository;

    private final PlayListTagRepository playListTagRepository;

    private final UserRepository userRepository;

    public PlayListServiceImpl(PlayListLikeRepository playListLikeRepository,
        PlayListRepository playListRepository, PlayListSongRepository playListSongRepository,
        PlayListTagRepository playListTagRepository, UserRepository userRepository) {
        this.playListLikeRepository = playListLikeRepository;
        this.playListRepository = playListRepository;
        this.playListSongRepository = playListSongRepository;
        this.playListTagRepository = playListTagRepository;
        this.userRepository = userRepository;
    }

    // playList 등록
    @Override
    public PlayList registPlayList(PlayListRegistPostReq registInfo) {

        log.info("PlayListService_PlayListRegist_start : " + registInfo.toString());

        User user = userRepository.findById(registInfo.getUserId())
            .orElseThrow(UserNotFoundException::new);
        String title =registInfo.getTitle();
        String description =registInfo.getDescription();
        Integer isPublic = registInfo.getIsPublic();
        // img url, 이미지 저장 로직은 아직 미완
        String imgUrl = "";

        PlayList playList = PlayList.builder()
            .user(user)
            .isPublic(isPublic)
            .title(title)
            .description(description)
            .imgUrl(imgUrl)
            .build();

        playListRepository.save(playList);

        log.info("PlayListService_PlayListRegist_end : " + playList.toString());

        return playList;

    }

    //PlayList만 수정
    @Override
    public void modifyPlayList(PlayListModifyPutReq modifyInfo) {

        log.info("PlayListService_PlayListModify_start : " + modifyInfo.toString());

        PlayList playList = playListRepository.findById(modifyInfo.getPlayListId())
            .orElseThrow(PlayListNotFoundException::new);
        playList.modifyPlayList(modifyInfo);

        log.info("PlayListService_PlayListModify_end : " + playList.toString());
    }

    @Override
    public void modifyPlayListImg(PlayListImgModifyPutReq modifyInfo) {
        //이미지 변경 로직, 이미지 저장 하면서, 새로운 url 제공

    }

    @Override
    public void deletePlayList(PlayListDeletePutReq deleteInfo) {

        log.info("PlayListService_PlayListDelete_start : " + deleteInfo.toString());

        PlayList playList = playListRepository.findById(deleteInfo.getPlayListId())
            .orElseThrow(PlayListNotFoundException::new);
        playList.deletePlayList();

        log.info("PlayListService_PlayListDelete_end : " + playList.toString());

    }

    @Override
    public PlayListTag registPlayListTag(PlayListTagRegistPostReq registInfo) {

        log.info("PlayListService_PlayListTagSongRegist_start : " + registInfo.toString());

        PlayList playList = playListRepository.findById(registInfo.getPlayListId())
            .orElseThrow(PlayListNotFoundException::new);
        String tagName = registInfo.getTagName();

        PlayListTag playListTag = PlayListTag.builder()
            .playList(playList)
            .tagName(tagName)
            .build();

        playListTagRepository.save(playListTag);

        log.info("PlayListService_PlayListTagSongRegist_end : " + playListTag.toString());

        return playListTag;
    }

    @Override
    public void deletePlayListTag(PlayListTagDeletePutReq deleteInfo) {

        log.info("PlayListService_PlayListTagSongDelete_start : " + deleteInfo.toString());

        PlayListTag playListTag = playListTagRepository.findById(deleteInfo.getPlayListTagId())
            .orElseThrow(PlayListTagNotFoundException::new);
        playListTag.deletePlayListTag();

        log.info("PlayListService_PlayListTagSongDelete_end : " + deleteInfo.toString());
    }

    @Override
    public PlayListSong registSongInPlayList(PlayListSongRegistPostReq registInfo) {

        log.info("PlayListService_PlayListSongRegist_start : " + registInfo.toString());

//        Song song = songRepository.findById(registInfo.getSongId())
//            .orElseThrow(SongNotFoundException::new);

        PlayList playList = playListRepository.findById(registInfo.getPlayListId())
            .orElseThrow(PlayListNotFoundException::new);

//        PlayListSong playListSong = PlayListSong.builder()
//            .playlist(playList)
//            .song(song)
//            .build();

//        PlayListSong playListSong = playListSongRepository.findBySongAndPlayList(song, playList);
//        if (playListSong==null){
//            playListSongRepository.save(playListSong);
//        } else {
//            if (playListSong.getDeletedAt() != null) playListSong.setDeletedAt();
//            playListSongRepository.save(playListSong);
//        }
//        playListSongRepository.save(playListSong);
//        return playListSong;

//        log.info("PlayListService_PlayListSongRegist_end : " + playListSong.toString());

        return null;
    }

    @Override
    public void deletePlayListSong(PlayListSongDeletePutReq deleteInfo) {

        log.info("PlayListService_PlayListSongDelete_start : " + deleteInfo.toString());

//        Song song = songRepository.findById(deleteInfo.getSongId())
//            .orElseThrow(SongNotFoundException::new);
//
//        PlayList playList = playListRepository.findById(deleteInfo.getPlayListId())
//            .orElseThrow(PlayListNotFoundException::new);
//
//        PlayListSong playListSong = playListSongRepository.findBySongAndPlayList(song, playList)
//            .orElseThrow(PlayListSongNotFoundException::new);
//
//        if(playListSong.getDeletedAt()==null){
//            playListSong.deletePlayListSong();
//        }

        log.info("PlayListService_PlayListSongDelete_end");
    }

    @Override
    public void registPlayListLike(PlayListLikeRegistPostReq registInfo) {

        log.info("PlayListService_PlayListLikeRegist_start : " + registInfo.toString());

        User user = userRepository.findById(registInfo.getUserId())
            .orElseThrow(UserNotFoundException::new);

        PlayList playList = playListRepository.findById(registInfo.getPlayListId())
            .orElseThrow(PlayListNotFoundException::new);

        PlayListLike playListLike = playListLikeRepository.findByUserAndPlayList(user, playList);

        if (playListLike==null) {
            playListLike = PlayListLike.builder()
                .user(user)
                .playList(playList)
                .build();
            playListLikeRepository.save(playListLike);
            playList.incrementLikeCount();
        } else {
            playListLike.setDeletedAt();
            playList.incrementLikeCount();
        }

        log.info("PlayListService_PlayListLikeRegist_end");
    }

    @Override
    public void deletePlayListLike(PlayListLikeDeletePutReq deleteInfo) {

        log.info("PlayListService_PlayListLikeDelete_start : " + deleteInfo.toString());

        User user = userRepository.findById(deleteInfo.getUserId())
            .orElseThrow(UserNotFoundException::new);

        PlayList playList = playListRepository.findById(deleteInfo.getPlayListId())
            .orElseThrow(PlayListNotFoundException::new);

        PlayListLike playListLike = playListLikeRepository.findByUserAndPlayList(user, playList);

        if (playListLike.getDeletedAt()==null){
            playListLike.deletePlayListLike();
            playList.decrementLikeCount();
        }

        log.info("PlayListService_PlayListLikeDelete_end"  );
    }

    @Override
    public List<Song> getPlayListSong(Long playListId) {

        log.info("PlayListService_getPlayListSong_start : " + playListId);

        PlayList playList = playListRepository.findById(playListId)
            .orElseThrow(PlayListNotFoundException::new);

        List<PlayListSong> playListSongs = playListSongRepository.findByPlaylist(playList);

        // playListSongs 배열을 받아서 SongId 확인해서 다시 Song들을 반환할거임;
        //

        log.info("PlayListService_getPlayListSong_end");

        return null;
    }

    // 비로그인시 홈화면 투데이 추천 플레이리스트 7가지만 가져오기
    @Override
    public List<PlayList> getHomeRecommendPlayList() {

        log.info("PlayListService_getHomeRecommendPlayList_start");

        // 추천 로직 아직 모름 패스

        log.info("PlayListService_getHomeRecommendPlayList_end");

        return null;
    }

    // 로그인시 홈화면 투데이 추천 플레이리스트 7가지만 가져오기
    @Override
    public List<PlayList> getHomeRecommendPlayList(Long userId) {
        return null;
    }

    @Override
    public List<PlayList> getFollowerPlayList(Long userId) {

        log.info("PlayListService_getFollowerPlayList_start: " + userId);

        // follow랑 user 필요
//   user Id에 해당하는 List<User> 팔로우 유저들 리스트 가져와서 다시 findByUser로 List<playList>가져오기
        List<User> followingUsers = new ArrayList<>();

        log.info("PlayListService_getFollowerPlayList_end: " + followingUsers.toString());
        return null;
    }

    @Override
    public List<PlayList> getUserPlayList(Long userId) {

        log.info("PlayListService_getUserPlayList_start: " + userId);

        User user = userRepository.findById(userId)
            .orElseThrow(UserNotFoundException::new);

        List<PlayList> myPlayLists = playListRepository.findByUser(user);

        log.info("PlayListService_getUserPlayList_start: " + myPlayLists.toString());

        return myPlayLists;
    }

    @Override
    public Page<PlayListGetResponse> searchByTitlePlayList(String keyword, Pageable pageable) {

        log.info("PlayListService_searchByTitlePlayList_start: " + keyword + ", "
            + pageable.toString());

        Page<PlayListGetResponse> playListGetResponses = playListRepository.findByTitleContainingAndIsPublic(keyword, 1, pageable)
                .map(m -> PlayListGetResponse.builder()
                    .id(m.getId())
                    .user_id(m.getUser().getId())
                    .title(m.getTitle())
                    .description(m.getDescription())
                    .viewCount(m.getViewCount())
                    .likeCount(m.getLikeCount())
                    .imgUrl(m.getImgUrl())
                    .created_data(m.getCreatedData())
                    .build()
                );

        log.info("PlayListService_searchByTitlePlayList_end");
        return playListGetResponses;
    }

    @Override
    public Page<PlayListGetResponse> searchByTagPlayList(String keyword, Pageable pageable){

        log.info("PlayListService_searchByTagPlayList_start: " + keyword + ", "
            + pageable.toString());

        Page<PlayListGetResponse> playListGetResponses = playListRepository.findByPlayListTags_TagNameAndIsPublic(keyword, 1, pageable)
                .map(m -> PlayListGetResponse.builder()
                    .id(m.getId())
                    .user_id(m.getUser().getId())
                    .title(m.getTitle())
                    .description(m.getDescription())
                    .viewCount(m.getViewCount())
                    .likeCount(m.getLikeCount())
                    .imgUrl(m.getImgUrl())
                    .created_data(m.getCreatedData())
                    .build()
                );

        log.info("PlayListService_searchByTagPlayList_end");
        return playListGetResponses;
    }



}
