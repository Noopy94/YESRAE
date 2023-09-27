package com.ssafy.yesrae.domain.user.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.ssafy.yesrae.common.exception.DuplicateEmailException;
import com.ssafy.yesrae.common.exception.DuplicateNicknameException;
import com.ssafy.yesrae.common.exception.NotFoundException;
import com.ssafy.yesrae.common.exception.user.InvalidPasswordException;
import com.ssafy.yesrae.common.exception.user.UserNotFoundException;
import com.ssafy.yesrae.config.jwt.service.JwtService;
import com.ssafy.yesrae.domain.user.Role;
import com.ssafy.yesrae.domain.user.dto.request.UserFollowCheckGetReq;
import com.ssafy.yesrae.domain.user.dto.request.UserFollowPostReq;
import com.ssafy.yesrae.domain.user.dto.request.UserLoginPostReq;
import com.ssafy.yesrae.domain.user.dto.request.UserRegistPostReq;
import com.ssafy.yesrae.domain.user.dto.response.UserCheckEmailRes;
import com.ssafy.yesrae.domain.user.dto.response.UserCheckNicknameRes;
import com.ssafy.yesrae.domain.user.dto.response.UserFindRes;
import com.ssafy.yesrae.domain.user.dto.response.UserFollowFindRes;
import com.ssafy.yesrae.domain.user.dto.response.UserNicknameFindRes;
import com.ssafy.yesrae.domain.user.entity.User;
import com.ssafy.yesrae.domain.user.entity.UserFollow;
import com.ssafy.yesrae.domain.user.entity.UserFollowId;
import com.ssafy.yesrae.domain.user.repository.UserFollowRepository;
import com.ssafy.yesrae.domain.user.repository.UserRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    @Value("${jwt.secretKey}")
    private String secretKey;

    private final JwtService jwtService;

    private final UserRepository userRepository;
    private final UserFollowRepository userFollowRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void regist(UserRegistPostReq userRegistPostReq) {

        String email = userRegistPostReq.getEmail();
        String nickname = userRegistPostReq.getNickname();

        if (userRepository.findByEmail(email).isPresent()) {
            throw new DuplicateEmailException();
        }

        if (userRepository.findByNickname(nickname).isPresent()) {
            throw new DuplicateNicknameException();
        }

        User user = User.builder()
            .email(email)
            .password(userRegistPostReq.getPassword())
            .nickname(nickname)
            .age(userRegistPostReq.getAge())
            .role(Role.USER)
            .build();

        user.passwordEncode(passwordEncoder);
        userRepository.save(user);
    }

    @Override
    public UserCheckEmailRes checkEmail(String email) {
        log.info("UserService_checkEmail_start: email - " + email);

        User user = userRepository.findByEmail(email).orElse(null);

        if (user == null) {
            log.info("UserService_checkEmail_end: success");
            return UserCheckEmailRes.builder()
                .flag(true)
                .build();
        }

        log.info("UserService_checkEmail_end: Duplicated email");
        return UserCheckEmailRes.builder()
            .flag(false)
            .build();
    }

    @Override
    public UserCheckNicknameRes checkNickname(String nickname) {
        log.info("UserService_checkNickname_start: nickname - " + nickname);

        User user = userRepository.findByNickname(nickname).orElse(null);

        if (user == null) {
            log.info("UserService_checkNickname_end: success");
            return UserCheckNicknameRes.builder()
                .flag(true)
                .build();
        }

        log.info("UserService_checkNickname_end: Duplicated nickname");
        return UserCheckNicknameRes.builder()
            .flag(false)
            .build();
    }

    @Override
    public UserFindRes login(UserLoginPostReq userLoginPostReq) {

        log.info("UserService_login_start: " + userLoginPostReq.toString());

        User user = userRepository.findByEmail(userLoginPostReq.getEmail())
            .orElseThrow(UserNotFoundException::new);

        if (!passwordEncoder.matches(userLoginPostReq.getPassword(), user.getPassword())) {
            log.info("UserService_login_end: Invalid Password");
            throw new InvalidPasswordException();
        }

        String refreshToken = jwtService.createRefreshToken();

        user.updateRefreshToken(refreshToken);

        UserFindRes userFindRes = UserFindRes.builder()
            .id(user.getId())
            .email(user.getEmail())
            .nickname(user.getNickname())
            .imageUrl(user.getImageUrl())
            .age(user.getAge())
            .accessToken(jwtService.createAccessToken(userLoginPostReq.getEmail()))
            .refreshToken(refreshToken)
            .build();

        log.info("UserService_login_end: success");

        return userFindRes;
    }

    @Override
    public UserFindRes oauthLogin(String accessToken) {

        String alteredToken = Optional.of(accessToken)
            .filter(token -> token.startsWith("Bearer "))
            .map(token -> token.replace("Bearer ", ""))
            .orElseThrow(NotFoundException::new);

        String email = Optional.ofNullable(JWT.require(Algorithm.HMAC512(secretKey))
                .build()
                .verify(alteredToken)
                .getClaim("email")
                .asString())
            .orElseThrow(NotFoundException::new);

        User user = userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);

        return UserFindRes.builder()
            .id(user.getId())
            .email(user.getEmail())
            .nickname(user.getNickname())
            .imageUrl(user.getImageUrl())
            .age(user.getAge())
            .accessToken(alteredToken)
            .refreshToken(user.getRefreshToken())
            .build();
    }

    @Override
    public void follow(UserFollowPostReq userFollowPostReq) {

        log.info("UserService_follow_start: " + userFollowPostReq.toString());

        UserFollowId userFollowId = UserFollowId.builder()
            .user(userFollowPostReq.getId())
            .followerUser(userFollowPostReq.getFollowerId())
            .build();

        UserFollow userFollow = userFollowRepository.findById(userFollowId).orElse(null);

        if (userFollow == null) {
            User user = userRepository.findById(userFollowPostReq.getId())
                .orElseThrow(UserNotFoundException::new);
            User followerUser = userRepository.findById(userFollowPostReq.getFollowerId())
                .orElseThrow(UserNotFoundException::new);

            userFollowRepository.save(UserFollow.builder()
                .user(user)
                .followerUser(followerUser)
                .build());

            log.info("UserService_follow_end: success");
        }
    }

    @Override
    public List<UserFollowFindRes> findFollow(Long userId) {
        log.info("UserService_findFollow_start: userId - " + userId);

        List<UserFollow> userFollowList = userFollowRepository.findByUser(
            userRepository.findById(userId).orElseThrow(UserNotFoundException::new));

        List<UserFollowFindRes> userFollowFindResList = new ArrayList<>();

        for (UserFollow userFollow : userFollowList) {
            User followerUser = userFollow.getFollowerUser();
            userFollowFindResList.add(UserFollowFindRes.builder()
                .id(followerUser.getId())
                .nickname(followerUser.getNickname())
                .imageUrl(followerUser.getImageUrl())
                .build());
        }

        log.info("UserService_findFollow_end: success");

        return userFollowFindResList;
    }

    @Override
    public boolean checkFollow(UserFollowCheckGetReq userFollowCheckGetReq) {
        log.info("UserService_checkFollow_start: " + userFollowCheckGetReq.toString());

        UserFollowId userFollowId = UserFollowId.builder()
            .user(userFollowCheckGetReq.getId())
            .followerUser(userFollowCheckGetReq.getFollowerId())
            .build();

        UserFollow userFollow = userFollowRepository.findById(userFollowId).orElse(null);

        boolean isFollowed = userFollow != null;

        log.info("UserService_checkFollow_end: success");

        return isFollowed;
    }

    @Override
    public UserNicknameFindRes findNickname(Long userId) {

        log.info("UserService_findNickname_start: userId - " + userId);

        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);

        UserNicknameFindRes userNicknameFindRes = UserNicknameFindRes.builder()
            .nickname(user.getNickname())
            .build();

        log.info("UserService_findNickname_end: success");

        return userNicknameFindRes;
    }
}
