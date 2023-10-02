package com.ssafy.yesrae.domain.song.service;

import com.ssafy.yesrae.common.exception.song.SongNotFoundException;
import com.ssafy.yesrae.common.exception.song.SonglikeNotFoundException;
import com.ssafy.yesrae.common.exception.user.UserNotFoundException;
import com.ssafy.yesrae.domain.song.dto.request.SongFindGetReq;
import com.ssafy.yesrae.domain.song.dto.request.SonglikeChangePostReq;
import com.ssafy.yesrae.domain.song.dto.response.SongFindRes;
import com.ssafy.yesrae.domain.song.dto.response.SonglikeFindRes;
import com.ssafy.yesrae.domain.song.entity.Song;
import com.ssafy.yesrae.domain.song.entity.Songlike;
import com.ssafy.yesrae.domain.song.entity.SonglikeId;
import com.ssafy.yesrae.domain.song.repository.SongRepository;
import com.ssafy.yesrae.domain.song.repository.SonglikeRepository;
import com.ssafy.yesrae.domain.user.entity.User;
import com.ssafy.yesrae.domain.user.repository.UserRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Transactional
@Service
public class SongServiceImpl implements SongService {

    private final SongRepository songRepository;
    private final SonglikeRepository songlikeRepository;
    private final UserRepository userRepository;

    public SongServiceImpl(SongRepository songRepository, SonglikeRepository songlikeRepository,
        UserRepository userRepository) {
        this.songRepository = songRepository;
        this.songlikeRepository = songlikeRepository;
        this.userRepository = userRepository;
    }

    @Override
    public SongFindRes findSong(String songId) {

        log.info("SongService_findSong_start: " + songId);

        Song song = songRepository.findById(songId)
            .orElseThrow(SongNotFoundException::new);

        SongFindRes res = SongFindRes.builder()
            .id(song.getId())
            .name(song.getName())
            .albumId(song.getAlbumId())
            .albumName(song.getAlbumName())
            .artistId(song.getArtistId())
            .artistName(song.getArtistName())
            .imgUrl(song.getImgUrl())
            .previewUrl(song.getPreviewUrl())
            .releaseYear(song.getReleaseYear())
            .duration(song.getDuration())
            .popularity(song.getPopularity())
            .acousticness(song.getAcousticness())
            .danceability(song.getDanceability())
            .energy(song.getEnergy())
            .instrumentalness(song.getInstrumentalness())
            .tune(song.getTune())
            .liveness(song.getLiveness())
            .loudness(song.getLoudness())
            .mode(song.getMode())
            .speechiness(song.getSpeechiness())
            .tempo(song.getTempo())
            .timeSignature(song.getTimeSignature())
            .valence(song.getValence())
            .build();

        log.info("SongService_findSong_end: success");

        return res;
    }

    @Override
    public List<SonglikeFindRes> findSonglikeByUserId(Long userId) {

        log.info("SongService_findSonglikeByUserId_start: " + userId);

        // 다른 유저꺼 보는 경우는 어떻게 할건지?

        List<Songlike> songlikes = songlikeRepository.findByUserId(userId);

        List<SonglikeFindRes> res = new ArrayList<>();

        for (Songlike songlike : songlikes) {

            Song song = songlike.getSong();

            if (!songlike.getIsDeleted()) {
                res.add(SonglikeFindRes.builder()
                    .songId(song.getId())
                    .name(song.getName())
                    .albumId(song.getAlbumId())
                    .albumName(song.getAlbumName())
                    .artistId(song.getArtistId())
                    .artistName(song.getArtistName())
                    .imgUrl(song.getImgUrl())
                    .previewUrl(song.getPreviewUrl())
                    .duration(song.getDuration())
                    .releaseYear(song.getReleaseYear())
                    .build());
            }
        }

        log.info("SongService_findSonglikeByUserId_end: success");

        return res;
    }

    @Override
    public Integer findSonglikeBySongId(String songId) {

        log.info("SongService_findSonglikeBySongId_start: " + songId);

        List<Songlike> songlikes = songlikeRepository.findBySongId(songId);

        Integer res = 0;

        for (Songlike songlike : songlikes) {
            if (!songlike.getIsDeleted()) {
                res++;
            }
        }

        log.info("SongService_findSonglikeBySongId_end: success");
        return res;
    }

    @Override
    public Boolean isSongliked(SongFindGetReq songFindGetReq) {

        log.info("SongService_isSongliked_start: " + songFindGetReq.toString());

        Boolean res = !songlikeRepository.findById(
                SonglikeId.builder()
                    .song(songFindGetReq.getSongId())
                    .user(songFindGetReq.getUserId())
                    .build()).orElseThrow(SonglikeNotFoundException::new)
            .getIsDeleted();

        log.info("SongService_isSongliked_end: success");
        return res;
    }

    // 노래가 한 번이라도 좋아요 된 적 있는지 (DB에 들어와있는지) 체크합니다.
    @Override
    public Boolean isSonglikedOnce(SonglikeChangePostReq songlikeChangePostReq) {

        log.info("SongService_isSonglikedOnce_start: " + songlikeChangePostReq.toString());

        Boolean res = songlikeRepository.findById(SonglikeId.builder()
                .song(songlikeChangePostReq.getSongId())
                .user(songlikeChangePostReq.getUserId())
                .build())
            .isPresent();

        log.info("SongService_isSonglikedOnce_end : success");

        return res;
    }

    @Override
    public Songlike registSonglike(SonglikeChangePostReq songlikeChangePostReq) {

        log.info("SongService_registSonglike_start: " + songlikeChangePostReq.toString());

        User user = userRepository.findById(songlikeChangePostReq.getUserId()).orElseThrow(
            UserNotFoundException::new);

        Song song = songRepository.findById(songlikeChangePostReq.getSongId())
            .orElseThrow(SongNotFoundException::new);

        Songlike songlike = Songlike.builder().user(user).song(song).build();

        songlikeRepository.save(songlike);

        log.info("SongService_registSonglike_end: success");

        return songlike;
    }

    @Override
    public Boolean changeSonglike(SonglikeChangePostReq songlikeChangePostReq) {

        log.info("SongService_changeSonglike_start: " + songlikeChangePostReq.toString());

        Songlike songlike = songlikeRepository.findById(
                SonglikeId.builder()
                    .user(songlikeChangePostReq.getUserId())
                    .song(songlikeChangePostReq.getSongId())
                    .build())
            .orElseThrow(SonglikeNotFoundException::new);

        if (songlike.getUser().getId().equals(songlikeChangePostReq.getUserId())) {
            songlike.changeSonglike();
        } else {
            return false;
        }

        log.info("SongService_changeSonglike_end: success");
        return true;
    }
}
