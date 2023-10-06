package com.ssafy.yesrae.domain.user.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class UserFollowPostReq {

    private Long id;
    private Long followerId;
}
