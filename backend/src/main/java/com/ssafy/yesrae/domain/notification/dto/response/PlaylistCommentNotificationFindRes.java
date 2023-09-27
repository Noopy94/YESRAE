package com.ssafy.yesrae.domain.notification.dto.response;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PlaylistCommentNotificationFindRes {

    private Long id;

    private Long userId;

    private Long playlistId;

    private LocalDateTime createdAt;

    private Boolean isViewed;

}
