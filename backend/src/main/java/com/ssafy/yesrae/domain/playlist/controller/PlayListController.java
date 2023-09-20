package com.ssafy.yesrae.domain.playlist.controller;

import com.ssafy.yesrae.common.exception.Template.TemplateNoResultException;
import com.ssafy.yesrae.common.exception.Template.TemplatePossessionFailException;
import com.ssafy.yesrae.common.model.CommonResponse;
import com.ssafy.yesrae.domain.playlist.service.PlayListService;
import com.ssafy.yesrae.domain.template.dto.request.TemplateRegistPostReq;
import com.ssafy.yesrae.domain.template.dto.response.TemplateFindRes;
import com.ssafy.yesrae.domain.template.service.TemplateService;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Template Controller
 */
@Slf4j
@RestController
@RequestMapping("/playlist")
public class PlayListController {

    // log 형식은 협의해서 새로 결정해도 무방
    private static final String SUCCESS = "success"; // API 성공 시 return

    private final PlayListService playListService;

    @Autowired
    public PlayListController(PlayListService playListService) {
        this.playListService = playListService;
    }

}
