package com.ssafy.yesrae.domain.notification.dto.response;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NotificationFindRes {

    private Long id;

    private String title;

    private String content;

    private LocalDateTime createAt;

    private Boolean isViewed;
}
