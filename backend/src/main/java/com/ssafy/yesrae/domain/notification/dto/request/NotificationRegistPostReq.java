package com.ssafy.yesrae.domain.notification.dto.request;

import lombok.Data;

@Data
public class NotificationRegistPostReq {

    private Long recipientId;

    private String title;

    private String content;

}
