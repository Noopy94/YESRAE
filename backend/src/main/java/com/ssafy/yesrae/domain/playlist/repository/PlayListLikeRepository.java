package com.ssafy.yesrae.domain.playlist.repository;

import com.ssafy.yesrae.domain.playlist.entity.PlayList;
import com.ssafy.yesrae.domain.playlist.entity.PlayListLike;
import com.ssafy.yesrae.domain.playlist.entity.PlayListSong;
import com.ssafy.yesrae.domain.template.entity.TemplateArticle;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/*
    TemplateArticle Entity를 위한 Spring Data JPA Repository
 */
@Repository
public interface PlayListLikeRepository extends JpaRepository<PlayListLike, Long> {

    // PlayList에 있는 PlayListLike 숫자세기
    Long countByPlayList(PlayList playList);

}
