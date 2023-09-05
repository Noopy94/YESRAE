package com.ssafy.yesrae.crawling.api.song.service;

import com.ssafy.yesrae.crawling.api.song.request.SongRegistPostReq;
import com.ssafy.yesrae.crawling.db.entity.Song;
import com.ssafy.yesrae.crawling.db.repository.SongRepository;
import java.time.LocalDate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Transactional
@Service
public class SongServiceImpl implements SongService{

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
        String genre = songInfo.getGenre();
        String imgUrl = songInfo.getImgUrl();
        String previewUrl = songInfo.getPreviewUrl();
        LocalDate releaseDate = songInfo.getReleaseDate();
        Integer duration = songInfo.getDuration();
        Integer popularity = songInfo.getPopularity();

        Song song = Song.builder()
            .id(id)
            .name(name)
            .albumId(albumId)
            .albumName(albumName)
            .artistId(artistId)
            .artistName(artistName)
            .genre(genre)
            .imgUrl(imgUrl)
            .previewUrl(previewUrl)
            .releaseDate(releaseDate)
            .duration(duration)
            .popularity(popularity)
            .build();

        songRepository.save(song);

        log.info("TemplateService_registTemplate_end: success");
        return song;
    }
}
