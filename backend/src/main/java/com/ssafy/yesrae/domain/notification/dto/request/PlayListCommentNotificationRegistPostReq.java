package com.ssafy.yesrae.domain.notification.dto.request;

import lombok.Data;

@Data
public class PlayListCommentNotificationRegistPostReq {

    private Long playListId;

    private Long userId;

}
