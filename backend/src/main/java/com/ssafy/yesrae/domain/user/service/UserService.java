package com.ssafy.yesrae.domain.user.service;

import com.ssafy.yesrae.domain.user.dto.request.UserFollowCheckGetReq;
import com.ssafy.yesrae.domain.user.dto.request.UserFollowPostReq;
import com.ssafy.yesrae.domain.user.dto.request.UserLoginPostReq;
import com.ssafy.yesrae.domain.user.dto.request.UserModifyProfilePutReq;
import com.ssafy.yesrae.domain.user.dto.request.UserRegistPostReq;
import com.ssafy.yesrae.domain.user.dto.response.UserCheckEmailRes;
import com.ssafy.yesrae.domain.user.dto.response.UserCheckNicknameRes;
import com.ssafy.yesrae.domain.user.dto.response.UserFindRes;
import com.ssafy.yesrae.domain.user.dto.response.UserFollowFindRes;
import com.ssafy.yesrae.domain.user.dto.response.UserNicknameFindRes;
import java.util.List;

public interface UserService {

    void regist(UserRegistPostReq userRegistPostReq);

    UserFindRes oauthLogin(String accessToken);

    void follow(UserFollowPostReq userFollowPostReq);

    List<UserFollowFindRes> findFollow(Long userId);

    boolean checkFollow(UserFollowCheckGetReq userFollowCheckGetReq);

    UserNicknameFindRes findNickname(Long userId);

    UserFindRes login(UserLoginPostReq userLoginPostReq);

    UserCheckEmailRes checkEmail(String email);

    UserCheckNicknameRes checkNickname(String nickname);

    void modifyProfile(UserModifyProfilePutReq userModifyProfilePutReq);
}
