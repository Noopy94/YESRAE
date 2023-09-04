package com.ssafy.yesrae.api.template.response;

import lombok.Builder;
import lombok.Data;

/**
 * Template Response
 */
@Data
@Builder
public class TemplateFindRes {

    String title;

    String content;

    Integer viewCount;
}
