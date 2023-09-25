package com.ssafy.yesrae.domain.user.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.ssafy.yesrae.common.exception.DuplicateEmailException;
import com.ssafy.yesrae.common.exception.DuplicateNicknameException;
import com.ssafy.yesrae.common.exception.NotFoundException;
import com.ssafy.yesrae.common.exception.user.UserNotFoundException;
import com.ssafy.yesrae.domain.user.Role;
import com.ssafy.yesrae.domain.user.dto.request.UserRegistPostReq;
import com.ssafy.yesrae.domain.user.dto.response.UserFindRes;
import com.ssafy.yesrae.domain.user.entity.User;
import com.ssafy.yesrae.domain.user.repository.UserRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    @Value("${jwt.secretKey}")
    private String secretKey;

    private final UserRepository userRepository;
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
    public UserFindRes login(String accessToken) {

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
            .email(user.getEmail())
            .nickname(user.getNickname())
            .imageUrl(user.getImageUrl())
            .age(user.getAge())
            .accessToken(alteredToken)
            .refreshToken(user.getRefreshToken())
            .build();
    }
}
