package com.ssafy.yesrae.domain.comment.dto.request;

import lombok.Data;

@Data
public class PlayListCommentDeletePutReq {

    private Long playListCommentId;

    private Long userId;
}
