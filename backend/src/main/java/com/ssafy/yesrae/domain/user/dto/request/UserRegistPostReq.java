package com.ssafy.yesrae.domain.user.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class UserRegistPostReq {

    // 자체 로그인을 위한 dto
    private String email;
    private String password;
    private String nickname;
    private Integer age;
}
