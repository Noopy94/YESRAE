package com.ssafy.yesrae.common.exception.article;

import com.ssafy.yesrae.common.exception.ErrorCode;
import com.ssafy.yesrae.common.model.BaseException;

public class ArticleNotFoundException extends BaseException {

    public ArticleNotFoundException() {
        super(ErrorCode.ARTICLE_NOT_FOUND);
    }

}
