package com.ssafy.yesrae.domain.playlist.service;


import com.ssafy.yesrae.common.exception.playList.PlayListNotFoundException;
import com.ssafy.yesrae.common.exception.playList.PlayListTagNotFoundException;
import com.ssafy.yesrae.common.exception.user.UserNotFoundException;
import com.ssafy.yesrae.domain.playlist.dto.request.PlayListDeletePutReq;
import com.ssafy.yesrae.domain.playlist.dto.request.PlayListImgModifyPutReq;
import com.ssafy.yesrae.domain.playlist.dto.request.PlayListModifyPutReq;
import com.ssafy.yesrae.domain.playlist.dto.request.PlayListRegistPostReq;
import com.ssafy.yesrae.domain.playlist.dto.request.PlayListSongDeletePutReq;
import com.ssafy.yesrae.domain.playlist.dto.request.PlayListSongRegistPostReq;
import com.ssafy.yesrae.domain.playlist.dto.request.PlayListTagDeletePutReq;
import com.ssafy.yesrae.domain.playlist.dto.request.PlayListTagRegistPostReq;
import com.ssafy.yesrae.domain.playlist.entity.PlayList;
import com.ssafy.yesrae.domain.playlist.entity.PlayListSong;
import com.ssafy.yesrae.domain.playlist.entity.PlayListTag;
import com.ssafy.yesrae.domain.playlist.repository.PlayListLikeRepository;
import com.ssafy.yesrae.domain.playlist.repository.PlayListRepository;
import com.ssafy.yesrae.domain.playlist.repository.PlayListSongRepository;
import com.ssafy.yesrae.domain.playlist.repository.PlayListTagRepository;
import com.ssafy.yesrae.domain.user.entity.User;
import com.ssafy.yesrae.domain.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
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

        User user = userRepository.findById(registInfo.getUserId())
            .orElseThrow(UserNotFoundException::new);
        String title =registInfo.getTitle();
        String description =registInfo.getDescription();
        Boolean isPublic = registInfo.getIsPublic();
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
        return playList;

    }

    //PlayList만 수정
    @Override
    public void modifyPlayList(PlayListModifyPutReq modifyInfo) {

        log.info("ModifyInfo Title: "+modifyInfo.getTitle()+"Description :"+modifyInfo.getDescription());
        PlayList playList = playListRepository.findById(modifyInfo.getPlayListId())
            .orElseThrow(PlayListNotFoundException::new);
        playList.modifyPlayList(modifyInfo);
    }

    @Override
    public void modifyPlayListImg(PlayListImgModifyPutReq modifyInfo) {
        //이미지 변경 로직

    }

    @Override
    public void deletePlayList(PlayListDeletePutReq deleteInfo) {
        PlayList playList = playListRepository.findById(deleteInfo.getPlayListId())
            .orElseThrow(PlayListNotFoundException::new);
        playList.deletePlayList();
    }

    @Override
    public PlayListTag registPlayListTag(PlayListTagRegistPostReq registInfo) {

        PlayList playList = playListRepository.findById(registInfo.getPlayListId())
            .orElseThrow(PlayListNotFoundException::new);
        String tagName = registInfo.getTagName();

        PlayListTag playListTag = PlayListTag.builder()
            .playList(playList)
            .tagName(tagName)
            .build();

        playListTagRepository.save(playListTag);

        return playListTag;
    }

    @Override
    public void deletePlayListTag(PlayListTagDeletePutReq deleteInfo) {

        PlayListTag playListTag = playListTagRepository.findById(deleteInfo.getPlayListTagId())
            .orElseThrow(PlayListTagNotFoundException::new);
        playListTag.deletePlayListTag();
    }

    @Override
    public PlayListSong registSongInPlayList(PlayListSongRegistPostReq registInfo) {

        return null;
    }

    @Override
    public boolean deleteSongPlayList(PlayListSongDeletePutReq deleteInfo) {

        return false;
    }

    @Override
    public List<PlayListSong> getPlayListSong(Long playListId) {
        return null;
    }

    @Override
    public List<PlayList> getHomeRecommendPlayList() {
        return null;
    }

    @Override
    public List<PlayList> getHomeRecommendPlayList(Long userId) {
        return null;
    }

    @Override
    public List<PlayList> getFollowerPlayList(Long userId) {
        return null;
    }

    @Override
    public List<PlayList> getMyPlayList(Long userId) {
        return null;
    }

    @Override
    public List<PlayList> searchByTitlePlayList(String searchTitle) {
        return null;
    }

    @Override
    public List<PlayList> searchByTagPlayList(String searchTag) {
        return null;
    }
}
