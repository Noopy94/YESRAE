package com.ssafy.yesrae.domain.template.dto.response;

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
