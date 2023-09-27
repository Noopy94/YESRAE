package com.ssafy.yesrae.domain.user.controller;

import com.ssafy.yesrae.common.model.CommonResponse;
import com.ssafy.yesrae.domain.user.dto.request.UserFollowCheckGetReq;
import com.ssafy.yesrae.domain.user.dto.request.UserFollowPostReq;
import com.ssafy.yesrae.domain.user.dto.request.UserLoginPostReq;
import com.ssafy.yesrae.domain.user.dto.request.UserRegistPostReq;
import com.ssafy.yesrae.domain.user.dto.response.UserCheckEmailRes;
import com.ssafy.yesrae.domain.user.dto.response.UserCheckNicknameRes;
import com.ssafy.yesrae.domain.user.dto.response.UserFindRes;
import com.ssafy.yesrae.domain.user.dto.response.UserFollowCheckRes;
import com.ssafy.yesrae.domain.user.dto.response.UserFollowFindRes;
import com.ssafy.yesrae.domain.user.dto.response.UserNicknameFindRes;
import com.ssafy.yesrae.domain.user.service.UserService;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    private static final String SUCCESS = "success";

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping("/regist")
    public CommonResponse<?> registUser(@RequestBody UserRegistPostReq userRegistPostReq) {

        log.info("UserController_registUser_start: " + userRegistPostReq.toString());

        userService.regist(userRegistPostReq);

        log.info("UserController_registUser_end: success");

        return CommonResponse.success(SUCCESS);
    }

    @GetMapping("/email")
    public CommonResponse<?> checkEmail(@RequestParam String email) {
        log.info("UserController_checkEmail_start: email - " + email);

        UserCheckEmailRes userCheckEmailRes = userService.checkEmail(email);

        log.info("UserController_checkEmail_end: success");

        return CommonResponse.success(userCheckEmailRes);
    }

    @GetMapping("/nickname")
    public CommonResponse<?> checkNickname(@RequestParam String nickname) {
        log.info("UserController_checkEmail_start: nickname - " + nickname);

        UserCheckNicknameRes userCheckNicknameRes = userService.checkNickname(nickname);

        log.info("UserController_checkEmail_end: success");

        return CommonResponse.success(userCheckNicknameRes);
    }

    @PostMapping("/login")
    public CommonResponse<?> login(@RequestBody UserLoginPostReq userLoginPostReq) {

        log.info("UserController_oauthLogin_start: " + userLoginPostReq);

        UserFindRes userFindRes = userService.login(userLoginPostReq);

        return CommonResponse.success(userFindRes);
    }

    @PostMapping("/oauth2/login")
    public CommonResponse<?> oauthLogin(@RequestHeader("Authorization") String accessToken) {

        log.info("UserController_oauthLogin_start: accessToken - " + accessToken);

        UserFindRes userFindRes = userService.oauthLogin(accessToken);

        log.info("UserController_oauthLogin_end: " + userFindRes.toString());

        return CommonResponse.success(userFindRes);
    }

    @PostMapping("/follow")
    public CommonResponse<?> registFollow(@RequestBody UserFollowPostReq userFollowPostReq) {

        userService.follow(userFollowPostReq);

        return CommonResponse.success(SUCCESS);
    }

    @GetMapping("/follow/{userId}")
    public CommonResponse<?> findFollow(@PathVariable Long userId) {

        log.info("UserController_findFollow_start: userId - " + userId);

        List<UserFollowFindRes> userFollowFindResList = userService.findFollow(userId);

        log.info("UserController_findFollow_end: success");

        return CommonResponse.success(userFollowFindResList);
    }

    @GetMapping("/follow/check")
    public CommonResponse<?> checkFollow(UserFollowCheckGetReq userFollowCheckGetReq) {

        log.info("UserController_checkFollow_start: " + userFollowCheckGetReq.toString());

        UserFollowCheckRes userFollowCheckRes = UserFollowCheckRes.builder()
            .isFollowed(userService.checkFollow(userFollowCheckGetReq))
            .build();

        log.info("UserController_checkFollow_end: success");

        return CommonResponse.success(userFollowCheckRes);
    }

    @GetMapping("/nickname/{userId}")
    public CommonResponse<?> findNickname(@PathVariable Long userId) {

        log.info("UserController_findNickname_start: userId - " + userId);

        UserNicknameFindRes userNicknameFindRes = userService.findNickname(userId);

        log.info("UserController_findNickname_end: success");

        return CommonResponse.success(userNicknameFindRes);
    }
}
