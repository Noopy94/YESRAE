package com.ssafy.yesrae.domain.user.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserFindRes {
    private String email;
    private String nickname;
    private String imageUrl;
    private Integer age;
    private String accessToken;
    private String refreshToken;
}
