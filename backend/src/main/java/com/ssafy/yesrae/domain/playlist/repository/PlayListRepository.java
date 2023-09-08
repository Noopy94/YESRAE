package com.ssafy.yesrae.domain.playlist.repository;

import com.ssafy.yesrae.domain.playlist.entity.PlayList;
import com.ssafy.yesrae.domain.user.entity.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/*
    TemplateArticle Entity를 위한 Spring Data JPA Repository
 */
@Repository
public interface PlayListRepository extends JpaRepository<PlayList, Long> {

    List<PlayList> findByUser(User user); //Optional 필요없음 없으면 빈 list반환
    //파라미터로 받은 키워드를 포함(LIKE와 같음)하는 제목을 가진 글들을 반환 (페이지네이션)
    Page<PlayList> findByTitleContaining(String keyword, Pageable pageable);

}
