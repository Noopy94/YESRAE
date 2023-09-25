package com.ssafy.yesrae.common.util;

import com.ssafy.yesrae.common.exception.ImageUploadFailException;
import com.ssafy.yesrae.common.model.FileDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Component
public class FileUploader {

    private final S3Uploader s3Uploader;

    @Autowired
    public FileUploader(S3Uploader s3Uploader) {
        this.s3Uploader = s3Uploader;
    }

    public List<FileDto> fileUpload(List<MultipartFile> fileList, String subFolderName) {
        log.info("FileUploader_fileUpload_start: " + fileList + " " + subFolderName);

        List<FileDto> fileDtoList = new ArrayList<FileDto>();

        for (MultipartFile file : fileList) {
            try {
                String originalName = file.getOriginalFilename();
                // 파일 확장자 명
                String extension = originalName.substring( // 파일명에 .은 반드시 있으니 예외처리 X
                        originalName.lastIndexOf(".") + 1);

                // 랜덤한 파일 이름 생성
                String fileName = subFolderName + "/" + UUID.randomUUID() + "." + extension;

                // S3 파일 경로 설정
                String path = s3Uploader.upload(file, fileName);

                fileDtoList.add(FileDto.builder()
                        .fileName(fileName)
                        .originalName(file.getOriginalFilename())
                        .path(path)
                        .contentType(file.getContentType())
                        .build());
            } catch (IllegalStateException | IOException e) {
                log.info("FileUploader_fileUpload_end: false");
                throw new ImageUploadFailException(); // 이미지 등록 실패 시 Exception
            }
        }

        log.info("FileUploader_fileUpload_end: " + fileDtoList);
        return fileDtoList;
    }
}
