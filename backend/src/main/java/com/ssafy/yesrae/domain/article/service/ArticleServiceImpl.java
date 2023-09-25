package com.ssafy.yesrae.domain.article.service;

import com.ssafy.yesrae.common.exception.NoDataException;
import com.ssafy.yesrae.common.exception.NotFoundException;
import com.ssafy.yesrae.common.model.FileDto;
import com.ssafy.yesrae.common.util.FileUploader;
import com.ssafy.yesrae.domain.article.dto.request.ArticleModifyPutReq;
import com.ssafy.yesrae.domain.article.dto.request.ArticleRegistPostReq;
import com.ssafy.yesrae.domain.article.dto.response.ArticleFindRes;
import com.ssafy.yesrae.domain.article.entity.Article;
import com.ssafy.yesrae.domain.article.entity.Category;
import com.ssafy.yesrae.domain.article.entity.Photo;
import com.ssafy.yesrae.domain.article.repository.ArticleRepository;
import com.ssafy.yesrae.domain.article.repository.PhotoRepository;
import com.ssafy.yesrae.domain.article.repository.CategoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Transactional
@Service
public class ArticleServiceImpl implements ArticleService{

    private final ArticleRepository articleRepository;
    private final CategoryRepository categoryRepository;
    private final PhotoRepository photoRepository;
    private final FileUploader fileUploader;

    @Autowired
    public ArticleServiceImpl(ArticleRepository articleRepository, CategoryRepository categoryRepository, PhotoRepository photoRepository, FileUploader fileUploader) {
        this.articleRepository = articleRepository;
        this.categoryRepository = categoryRepository;
        this.photoRepository = photoRepository;
        this.fileUploader = fileUploader;
    }

    @Override
    public Article registArticle(ArticleRegistPostReq articleRegistPostReq, List<MultipartFile> files) {
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
        Category categoryEntity = categoryRepository.findById(articleRegistPostReq.getCategory())
                .orElseThrow(NotFoundException::new);
        Long category = articleRegistPostReq.getCategory();
        String content = articleRegistPostReq.getContent();
        String title = articleRegistPostReq.getTitle();

        Article article = Article.builder()
                .content(content)
                .title(title)
                .category(categoryEntity)
                .type(type)
                .build();
        articleRepository.save(article);
        article.insertArticle();


        /*
         *  files 를 바탕으로 ArticlePicture Entity 생성 시작
         */
        if (!Objects.isNull(files) && files.get(0).getSize() > 0) {
            List<FileDto> fileDtoList = fileUploader.fileUpload(files, "article");
            for (FileDto fileDto : fileDtoList) {
                Photo photo = Photo.builder()
                        .article(article)
                        .fileName(fileDto.getFileName())
                        .originalName(fileDto.getOriginalName())
                        .contentType(fileDto.getContentType())
                        .path(fileDto.getPath())
                        .build();

                photoRepository.save(photo);
            }
        }


        log.info("ArticleService_insertArticle_end: success");
        return article;
    }
    /**
     * 게시글 삭제 API
     *
     * @param Id : 게시판의 Id
     */
    @Override
    public Boolean deleteArticle(Long Id) {
        Article article = articleRepository.findById(Id)
                .orElseThrow(NotFoundException::new);
        if (article.getUser().getId().equals(Id)) {
            log.info("ArticleService_deleteArticle_start: ");

            article.deleteArticle();
            log.info("ArticleService_deleteArticle_end: true");
            return true;
        }
        log.info("ArticleService_deleteArticle_end: false");
        return false;
    }

    /**
     * 유저가 게시글의 상세 정보를 확인하기 위한 API 서비스
     *
     * @param Id : 게시판의 Id
     */
    @Override
    public ArticleFindRes findArticle(Long Id) {

        log.info("ArticleService_findArticle_start: " + Id);

        Article article = articleRepository.findById(Id)
                .orElseThrow(NotFoundException::new);
        List<String> files = new ArrayList<>();
        if(article.getType()){
            List<Photo> pl = photoRepository.findByArticleEntity_Id(Id);
            for(Photo p : pl){
                files.add(p.getPath());
            }
        }

        ArticleFindRes articleFindRes = ArticleFindRes.builder()
                .content(article.getContent())
                .tagName(article.getCategory().getTagName())
                .title(article.getTitle())
                .createdDate(article.getCreatedDate().toString())
                .files(files)
//                .nickname()
                .build();

        // 게시글 상세 정보 조회 결과
        log.info("ArticleService_findArticle_end: " + articleFindRes.toString());
        return articleFindRes;
    }

    /**
     * 유저가 게시글 수정하기 위한 API 서비스
     *
     * @param articleModifyPutReq : 게시판의 Id
     */
    @Override
    public boolean modifyArticle(ArticleModifyPutReq articleModifyPutReq, List<MultipartFile> files) {
        if (files != null) {
            log.info("ArticleService_modifyArticle_start: " + articleModifyPutReq.toString() + ", "
                    + files);
        } else {
            log.info("ArticleService_modifyArticle_start: " + articleModifyPutReq.toString());
        }

        Article article = articleRepository.findById(articleModifyPutReq.getId())
                .orElseThrow(NotFoundException::new);
        // TODO: 현재 로그인 유저의 id와 글쓴이의 id가 일치할 때
        if (article.getUser().getId().equals(articleModifyPutReq.getUserId())) {
        // 점포 수정
        Category category = categoryRepository.findById(articleModifyPutReq.getCategory())
                .orElseThrow(NullPointerException::new);
        article.modifyArticle(articleModifyPutReq.getTitle(), articleModifyPutReq.getContent(), category);
//          TODO : 사진 어떻게 처리할지 관리

        // 게시글 기존 사진 전부 삭제
        List<Photo> photoList = photoRepository.findByArticle(article);
        // DB 에서 사진 삭제 처리
        for (Photo photo : photoList) {
            photo.deletePhoto();
        }

        // 게시글 사진 다시 업로드
        if (!Objects.isNull(files) && files.get(0).getSize() > 0) {
            List<FileDto> fileDtoList = fileUploader.fileUpload(files, "article");
            for (FileDto fileDto : fileDtoList) {
                Photo photo = Photo.builder()
                        .article(article)
                        .fileName(fileDto.getFileName())
                        .originalName(fileDto.getOriginalName())
                        .path(fileDto.getPath())
                        .contentType(fileDto.getContentType())
                        .build();

                photoRepository.save(photo);
            }
        }
        log.info("ArticleService_modifyArticle_end: true");
        return true;
        }
        log.info("ArticleService_modifyArticle_end: false");
        return false;
    }

    /**
     *  게시글 전체 조회 API에 대한 서비스
     */
    @Override
    public List<ArticleFindRes> findAllArticle() {
        log.info("ArticleService_findAll_start: ");


        List<ArticleFindRes> res = articleRepository.findAll()
                .stream().map(m -> ArticleFindRes.builder()
                                .files(findArticleFiles(m))
                                .content(m.getContent())
                                .title(m.getTitle())
                                .tagName(m.getCategory().getTagName())
                                .createdDate(m.getCreatedDate().toString())
//                              .nickname()
                                .build()
                ).collect(Collectors.toList());

        log.info("ArticleService_findAll_end: success");
        return res;
    }


    /**
     * 파일 이름 List 생성을 위한 API
     */
    List<String> findArticleFiles(Article article){
        List<String> files = new ArrayList<>();
        if(article.getType()){
            List<Photo> pl = photoRepository.findByArticleEntity_Id(article.getId());
            for(Photo p : pl){
                files.add(p.getPath());
            }
        }
        return files;
    }
}
