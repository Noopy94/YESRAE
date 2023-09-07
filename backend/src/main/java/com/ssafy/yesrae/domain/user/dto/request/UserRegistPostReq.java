package com.ssafy.yesrae.domain.user.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class UserRegistPostReq {

    // 자체 로그인을 위한 dto
    private String email;
    private String password;
    private String nickname;
    private Integer age;
}
