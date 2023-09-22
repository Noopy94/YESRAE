package com.ssafy.yesrae.domain.user.service;

import com.ssafy.yesrae.domain.user.dto.request.UserRegistPostReq;

public interface UserService {

    public void regist(UserRegistPostReq userRegistPostReq);

    public void login(String accessToken);
}
