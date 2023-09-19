package com.ssafy.yesrae.domain.article.service;

import com.ssafy.yesrae.common.exception.NoDataException;
import com.ssafy.yesrae.common.exception.Template.NotFoundException;
import com.ssafy.yesrae.domain.article.dto.request.ArticleModifyPutReq;
import com.ssafy.yesrae.domain.article.dto.request.ArticleRegistPostReq;
import com.ssafy.yesrae.domain.article.dto.response.ArticleFindRes;
import com.ssafy.yesrae.domain.article.entity.ArticleEntity;
import com.ssafy.yesrae.domain.article.entity.PhotoEntity;
import com.ssafy.yesrae.domain.article.entity.TagEntity;
import com.ssafy.yesrae.domain.article.repository.ArticleRepository;
import com.ssafy.yesrae.domain.article.repository.PhotoRepository;
import com.ssafy.yesrae.domain.article.repository.TagRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Transactional
@Service
public class ArticleServiceImpl implements ArticleService{

    private final ArticleRepository articleRepository;
    private final TagRepository tagRepository;

    private final PhotoRepository photoRepository;

    public ArticleServiceImpl(ArticleRepository articleRepository, TagRepository tagRepository, PhotoRepository photoRepository) {
        this.articleRepository = articleRepository;
        this.tagRepository = tagRepository;
        this.photoRepository = photoRepository;
    }

    @Override
    public ArticleEntity registArticle(ArticleRegistPostReq articleRegistPostReq, MultipartFile files) {
        boolean type = false;
        if (files != null) {
            log.info("ArticleService_registArticle_start: " + articleRepository.toString() + ", "
                    + files);
        } else {
            log.info("ArticleService_registArticle_start: " + articleRepository.toString());
            type = true;
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

    /**
     * 유저가 게시글의 상세 정보를 확인하기 위한 API 서비스
     *
     * @param Id : 게시판의 Id
     */
    @Override
    public ArticleFindRes findArticle(Long Id) {

        log.info("ArticleService_findArticle_start: " + Id);

        ArticleEntity articleEntity= articleRepository.findById(Id)
                .orElseThrow(NotFoundException::new);
        List<String> files = new ArrayList<>();
        if(articleEntity.getType()){
            List<PhotoEntity> pl = photoRepository.findByArticleEntity_Id(Id);
            for(PhotoEntity p : pl){
                files.add(p.getImage());
            }
        }

        ArticleFindRes articleFindRes = ArticleFindRes.builder()
                .content(articleEntity.getContent())
                .tagName(articleEntity.getTagEntity().getTagName())
                .title(articleEntity.getTitle())
                .createdDate(articleEntity.getCreatedDate())
                .files(files)
//                .nickname()
                .build();

        // 게시글 상세 정보 조회 결과
        log.info("ArticleService_findArticle_end: " + articleFindRes.toString());
        return articleFindRes;
    }

    @Override
    public boolean modifyArticle(ArticleModifyPutReq articleModifyPutReq, MultipartFile files) {
        if (files != null) {
            log.info("ArticleService_modifyArticle_start: " + articleModifyPutReq.toString() + ", "
                    + files);
        } else {
            log.info("ArticleService_modifyArticle_start: " + articleModifyPutReq.toString());
        }

        ArticleEntity articleEntity = articleRepository.findById(articleModifyPutReq.getId())
                .orElseThrow(NotFoundException::new);
        // TODO: 현재 로그인 유저의 id와 글쓴이의 id가 일치할 때
//        if (storeEntity.getUser().getId().equals(modifyInfo.getUserId())) {
        // 점포 수정
        TagEntity tagEntity = tagRepository.findById(articleModifyPutReq.getCategory())
                .orElseThrow(NullPointerException::new);
        articleEntity.modifyArticle(articleModifyPutReq.getTitle(), articleModifyPutReq.getContent(), tagEntity);
//          TODO : 사진 어떻게 처리할지 관리
        log.info("ArticleService_modifyArticle_end: true");
        return true;
//        }
//        log.info("StoreService_modifyStore_end: false");
//        return false;
    }

}
