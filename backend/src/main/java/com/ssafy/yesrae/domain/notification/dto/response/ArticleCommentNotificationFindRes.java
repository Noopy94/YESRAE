package com.ssafy.yesrae.domain.notification.dto.response;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ArticleCommentNotificationFindRes {

    private Long id;

    private Long userId;

    private Long commentId;

    private LocalDateTime createdAt;

    private Boolean isViewed;
    
}
