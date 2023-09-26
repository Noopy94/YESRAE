package com.ssafy.yesrae.domain.comment.dto.response;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PlayListCommentNotificationFindRes {

    private Long userId;

    private Long playListId;

    private LocalDateTime createdAt;

    private Boolean isViewed;
}
