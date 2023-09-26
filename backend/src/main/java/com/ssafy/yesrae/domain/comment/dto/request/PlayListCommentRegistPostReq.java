package com.ssafy.yesrae.domain.comment.dto.request;

import lombok.Data;

@Data
public class PlayListCommentRegistPostReq {

    private Long playListId;

    private Long userId;

    private String content;

}
