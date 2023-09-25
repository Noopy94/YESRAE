package com.ssafy.yesrae.domain.user.service;

import com.ssafy.yesrae.domain.user.dto.request.UserRegistPostReq;
import com.ssafy.yesrae.domain.user.dto.response.UserFindRes;

public interface UserService {

    public void regist(UserRegistPostReq userRegistPostReq);

    public UserFindRes login(String accessToken);
}
