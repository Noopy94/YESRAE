package com.ssafy.yesrae.domain.playlist.repository;

import com.ssafy.yesrae.domain.playlist.dto.response.PlaylistGetResponse;
import com.ssafy.yesrae.domain.playlist.entity.Playlist;
import com.ssafy.yesrae.domain.user.entity.User;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/*
    TemplateArticle Entity를 위한 Spring Data JPA Repository
 */
@Repository
public interface PlaylistRepository extends JpaRepository<Playlist, Long> {

    List<Playlist> findByUser(User user); //Optional 필요없음 없으면 빈 list반환

    //파라미터로 받은 키워드를 포함(LIKE와 같음)하는 제목을 가진 글들을 반환 (페이지네이션)
    Page<Playlist> findByTitleContainingAndIsPublic(String keyword, Integer isPublic,
        Pageable pageable);

    //    likeCount 기준 정렬해서 20개 가져오기
    List<Playlist> findTop20ByOrderByLikeCountDesc();

    //   viewCount 기준으로 20개 가져오기
    List<Playlist> findTop20ByOrderByViewCountDesc();

    @Query(
        "SELECT new com.ssafy.yesrae.domain.playlist.dto.response.PlaylistGetResponse(p.id, p.user.id, p.title, p.description, p.viewCount, p.likeCount, p.imgUrl) "
            +
            "FROM Playlist p WHERE p.id IN :ids")
    Page<PlaylistGetResponse> findAllByIdInCustom(List<Long> ids, Pageable pageable);


}
