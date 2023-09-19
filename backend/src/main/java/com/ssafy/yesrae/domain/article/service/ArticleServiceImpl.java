package com.ssafy.yesrae.domain.article.service;

import com.ssafy.yesrae.common.exception.NoDataException;
import com.ssafy.yesrae.domain.article.dto.request.ArticleRegistPostReq;
import com.ssafy.yesrae.domain.article.entity.ArticleEntity;
import com.ssafy.yesrae.domain.article.repository.ArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;

@Slf4j
@Transactional
@Service
public class ArticleServiceImpl implements ArticleService{

    private final ArticleRepository articleRepository;

    public ArticleServiceImpl(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    @Override
    public ArticleEntity registArticle(ArticleRegistPostReq registInfo, MultipartFile file) {
        if (file != null) {
            log.info("ArticleService_registArticle_start: " + registInfo.toString() + ", "
                    + file);
        } else {
            log.info("ArticleService_registArticle_start: " + registInfo.toString());
        }
        //TODO 작성자 정보 가져오기
//        User user = userRepository.findById(registInfo.getUserId())
//                .orElseThrow(UserNotFoundException::new);
        int category = registInfo.getCategory();
        String content = registInfo.getContent();
        String title = registInfo.getTitle();
        int type = registInfo.getType();

//        //TODO : 파일 저장
//        if (!Objects.isNull(fileList) && fileList.getSize() > 0) {
//            String imgPath = s3Service.saveFile(fileList);
//            stImg = "URL"+imgPath;
//        }
        ArticleEntity articleEntity = ArticleEntity.builder()
                .category(category)
                .content(content)
                .title(title)
                .type(type)
                .build();
        articleRepository.save(articleEntity);
        articleEntity.insertArticle();
        log.info("StoreService_insertStore_end: success");
        return articleEntity;
    }

    @Override
    public Boolean deleteArticle(Long Id) {
        log.info("ArticleService_deleteArticle_start: ");
        ArticleEntity articleEntity = articleRepository.findById(Id)
                .orElseThrow(NoDataException::new);

        articleEntity.deleteArticle();
        log.info("ArticleService_deleteArticle_end: true");
        return true;
    }
}
