package com.ssafy.yesrae.domain.user.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class UserLoginPostReq {

    private String email;
    private String password;
}
