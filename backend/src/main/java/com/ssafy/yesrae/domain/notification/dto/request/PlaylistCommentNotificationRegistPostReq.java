package com.ssafy.yesrae.domain.notification.dto.request;

import lombok.Data;

@Data
public class PlaylistCommentNotificationRegistPostReq {

    private Long playlistId;

    private Long userId;

}
