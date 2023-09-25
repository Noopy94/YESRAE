package com.ssafy.yesrae.domain.playlist.repository;

import com.ssafy.yesrae.domain.playlist.entity.PlayList;
import com.ssafy.yesrae.domain.playlist.entity.PlayListTag;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/*
    TemplateArticle Entity를 위한 Spring Data JPA Repository
 */
@Repository
public interface PlayListTagRepository extends JpaRepository<PlayListTag, Long> {

    // PlayList에 있는 태그들 가져오기
    List<PlayListTag> findByPlayList(PlayList playList);

    List<PlayListTag> findByTagName(String tagName);

}
