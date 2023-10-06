package com.ssafy.yesrae.common.exception.comment;

import com.ssafy.yesrae.common.exception.ErrorCode;
import com.ssafy.yesrae.common.model.BaseException;

public class CommentNotFoundException extends BaseException {
    
    public CommentNotFoundException() {
        super(ErrorCode.Comment_NOT_FOUND);
    }

}
