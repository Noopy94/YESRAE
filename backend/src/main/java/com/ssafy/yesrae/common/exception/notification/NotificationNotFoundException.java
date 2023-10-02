package com.ssafy.yesrae.common.exception.notification;

import com.ssafy.yesrae.common.exception.ErrorCode;
import com.ssafy.yesrae.common.model.BaseException;

public class NotificationNotFoundException extends BaseException {

    public NotificationNotFoundException() {
        super(ErrorCode.Notification_NOT_FOUND);
    }

}