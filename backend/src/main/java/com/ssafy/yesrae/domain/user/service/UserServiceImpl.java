package com.ssafy.yesrae.domain.user.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.ssafy.yesrae.common.exception.DuplicateEmailException;
import com.ssafy.yesrae.common.exception.DuplicateNicknameException;
import com.ssafy.yesrae.common.exception.NotFoundException;
import com.ssafy.yesrae.domain.user.Role;
import com.ssafy.yesrae.domain.user.dto.request.UserRegistPostReq;
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
    public User login(String accessToken) {

        String email = Optional.ofNullable(JWT.require(Algorithm.HMAC512(secretKey))
                .build()
                .verify(Optional.of(accessToken)
                    .filter(token -> token.startsWith("Bearer "))
                    .map(token -> token.replace("Bearer " , ""))
                    .orElseThrow(IllegalArgumentException::new))
                .getClaim("email")
                .asString())
            .orElseThrow(IllegalArgumentException::new);

        // TODO: email로 유저 찾아서 유저 정보 return

        return userRepository.findByEmail(email).orElseThrow(NotFoundException::new);
    }
}
