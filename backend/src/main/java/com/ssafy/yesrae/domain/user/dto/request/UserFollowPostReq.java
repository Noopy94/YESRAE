package com.ssafy.yesrae.domain.user.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@Getter
@ToString
public class UserFollowPostReq {

    private Long id;
    private Long followerId;
}
