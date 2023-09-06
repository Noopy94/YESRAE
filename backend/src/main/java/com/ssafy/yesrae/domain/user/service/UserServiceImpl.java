package com.ssafy.yesrae.domain.user.service;

import com.ssafy.yesrae.common.exception.DuplicateEmailException;
import com.ssafy.yesrae.common.exception.DuplicateNicknameException;
import com.ssafy.yesrae.domain.user.Role;
import com.ssafy.yesrae.domain.user.dto.request.UserRegistPostReq;
import com.ssafy.yesrae.domain.user.entity.User;
import com.ssafy.yesrae.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

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
}
