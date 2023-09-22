package com.ssafy.yesrae.crawling.domain.song.service;

import com.ssafy.yesrae.crawling.domain.song.dto.request.SongRegistPostReq;
import com.ssafy.yesrae.crawling.domain.song.dto.response.SongFindRes;
import com.ssafy.yesrae.crawling.domain.song.entity.Song;
import com.ssafy.yesrae.crawling.domain.song.repository.SongRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Transactional
@Service
public class SongServiceImpl implements SongService {

    private final SongRepository songRepository;

    @Autowired
    public SongServiceImpl(SongRepository songRepository) {
        this.songRepository = songRepository;
    }

    @Override
    public Song registSong(SongRegistPostReq songInfo) {
        log.info("SongService_registSong_start: " + songInfo.toString());

        String id = songInfo.getId();
        String name = songInfo.getName();
        String albumId = songInfo.getAlbumId();
        String albumName = songInfo.getAlbumName();
        String artistId = songInfo.getArtistId();
        String artistName = songInfo.getArtistName();
        String imgUrl = songInfo.getImgUrl();
        String previewUrl = songInfo.getPreviewUrl();
        Integer releaseYear = songInfo.getReleaseYear();
        Integer duration = songInfo.getDuration();
        Integer popularity = songInfo.getPopularity();
        Float acousticness = songInfo.getAcousticness();
        Float danceability = songInfo.getDanceability();
        Float energy = songInfo.getEnergy();
        Float instrumentalness = songInfo.getInstrumentalness();
        Integer tune = songInfo.getTune();
        Float liveness = songInfo.getLiveness();
        Float loudness = songInfo.getLoudness();
        Integer mode = songInfo.getMode();
        Float speechiness = songInfo.getSpeechiness();
        Float tempo = songInfo.getTempo();
        Integer timeSignature = songInfo.getTimeSignature();
        Float valence = songInfo.getValence();
        Boolean todaySong = songInfo.getTodaySong();

        Song song = Song.builder()
            .id(id)
            .name(name)
            .albumId(albumId)
            .albumName(albumName)
            .artistId(artistId)
            .artistName(artistName)
            .imgUrl(imgUrl)
            .previewUrl(previewUrl)
            .releaseYear(releaseYear)
            .duration(duration)
            .popularity(popularity)
            .acousticness(acousticness)
            .danceability(danceability)
            .energy(energy)
            .instrumentalness(instrumentalness)
            .tune(tune)
            .liveness(liveness)
            .loudness(loudness)
            .mode(mode)
            .speechiness(speechiness)
            .tempo(tempo)
            .timeSignature(timeSignature)
            .valence(valence)
            .todaySong(todaySong)
            .build();

        songRepository.save(song);

        log.info("SongService_registSong_end: success");
        return song;
    }

    @Override
    public List<SongFindRes> findSongByArtistId(String artistId) {
        List<Song> songs = songRepository.findByArtistId(artistId);
        List<SongFindRes> findSongs = new ArrayList<>();
        for (Song song : songs) {
            findSongs.add(SongFindRes.builder()
                .id(song.getId())
                .name(song.getName())
                .artistId(song.getArtistId())
                .popularity(song.getPopularity())
                .build());
        }
        return findSongs;
    }

    @Override
    public void deleteSong(String Id) {
        songRepository.delete(
            songRepository.findById(Id).orElseThrow(IllegalArgumentException::new));
        System.out.println(Id + "이 노래 지웠습니다.");
    }

    @Override
    public List<Song> findAllSong() {
        return songRepository.findAll();
    }
}
