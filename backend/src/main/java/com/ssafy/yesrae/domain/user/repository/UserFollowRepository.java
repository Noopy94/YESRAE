package com.ssafy.yesrae.domain.user.repository;

import com.ssafy.yesrae.domain.user.entity.User;
import com.ssafy.yesrae.domain.user.entity.UserFollow;
import com.ssafy.yesrae.domain.user.entity.UserFollowId;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserFollowRepository extends JpaRepository<UserFollow, UserFollowId> {

    List<UserFollow> findByUser(User user);
}
