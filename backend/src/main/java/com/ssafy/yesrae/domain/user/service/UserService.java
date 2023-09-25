package com.ssafy.yesrae.domain.user.service;

import com.ssafy.yesrae.domain.user.dto.request.UserRegistPostReq;
import com.ssafy.yesrae.domain.user.entity.User;

public interface UserService {

    public void regist(UserRegistPostReq userRegistPostReq);

    public User login(String accessToken);
}
