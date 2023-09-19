package com.ssafy.yesrae.domain.article.service;

import com.ssafy.yesrae.common.exception.NoDataException;
import com.ssafy.yesrae.common.exception.Template.NotFoundException;
import com.ssafy.yesrae.domain.article.dto.request.ArticleRegistPostReq;
import com.ssafy.yesrae.domain.article.entity.ArticleEntity;
import com.ssafy.yesrae.domain.article.entity.TagEntity;
import com.ssafy.yesrae.domain.article.repository.ArticleRepository;
import com.ssafy.yesrae.domain.article.repository.TagRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Transactional
@Service
public class ArticleServiceImpl implements ArticleService{

    private final ArticleRepository articleRepository;
    private final TagRepository tagRepository;

    public ArticleServiceImpl(ArticleRepository articleRepository, TagRepository tagRepository) {
        this.articleRepository = articleRepository;
        this.tagRepository = tagRepository;
    }

    @Override
    public ArticleEntity registArticle(ArticleRegistPostReq articleRegistPostReq, MultipartFile file) {
        if (file != null) {
            log.info("ArticleService_registArticle_start: " + articleRepository.toString() + ", "
                    + file);
        } else {
            log.info("ArticleService_registArticle_start: " + articleRepository.toString());
        }
        //TODO 작성자 정보 가져오기
//        User user = userRepository.findById(registInfo.getUserId())
//                .orElseThrow(UserNotFoundException::new);
        TagEntity tagEntity = tagRepository.findById(articleRegistPostReq.getCategory())
                .orElseThrow(NotFoundException::new);
        Long category = articleRegistPostReq.getCategory();
        String content = articleRegistPostReq.getContent();
        String title = articleRegistPostReq.getTitle();

//        //TODO : 파일 저장
//        if (!Objects.isNull(fileList) && fileList.getSize() > 0) {
//            String imgPath = s3Service.saveFile(fileList);
//            stImg = "URL"+imgPath;
//        }
        ArticleEntity articleEntity = ArticleEntity.builder()
                .content(content)
                .title(title)
                .tagEntity(tagEntity)
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
