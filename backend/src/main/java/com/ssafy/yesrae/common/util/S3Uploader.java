package com.ssafy.yesrae.common.util;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.ssafy.yesrae.common.exception.NoDataException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RequiredArgsConstructor
@Component
public class S3Uploader {

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;
    private final AmazonS3Client amazonS3Client;

    /**
     * @param multipartFile 업로드 할 파일
     * @param fileName      파일 위치 이름
     * @return url          업로드 된 파일의 url
     * @throws IOException 파일 입출력 에러
     */
    public String upload(MultipartFile multipartFile, String fileName) throws IOException {
        log.info("S3Uploader_upload_start(MultipartFile): " + fileName + " - " + multipartFile);

        File uploadFile = convert(multipartFile)
            .orElseThrow(NoDataException::new);

        log.info("S3Uploader_upload_end(MultipartFile): " + uploadFile);
        return upload(uploadFile, fileName);
    }

    private String upload(File uploadFile, String fileName) {
        log.info("S3Uploader_upload_start(File): " + fileName + " - " + uploadFile);

        String uploadImageUrl = putS3(uploadFile, fileName);
        removeNewFile(uploadFile);

        log.info("S3Uploader_upload_end(url): " + uploadImageUrl);
        return uploadImageUrl;
    }

    public void delete(String currentFilePath) {
        if (!"".equals(currentFilePath) && currentFilePath != null) {
            boolean isExistObject = amazonS3Client.doesObjectExist(bucket, currentFilePath);

            if (isExistObject) {
                amazonS3Client.deleteObject(bucket, currentFilePath);
            }
        }
    }

    private String putS3(File uploadFile, String fileName) {
        log.info("S3Uploader_putS3_start(fileName, uploadFile): " + fileName + " - " + uploadFile);
        amazonS3Client.putObject(new PutObjectRequest(bucket, fileName, uploadFile).withCannedAcl(
            CannedAccessControlList.PublicRead));
        log.info("S3Uploader_putS3_end");
        return amazonS3Client.getUrl(bucket, fileName).toString();
    }

    private void removeNewFile(File targetFile) {
        targetFile.delete();
    }

    private Optional<File> convert(MultipartFile file) throws IOException {
        log.info("S3Uploader_convert_start(file): " + file);
        File convertFile = new File(Objects.requireNonNull(file.getOriginalFilename()));
        log.debug("convertFile: " + convertFile);

        if (convertFile.createNewFile()) {
            try (FileOutputStream fos = new FileOutputStream(convertFile)) {
                fos.write(file.getBytes());
            }
            log.info("S3Uploader_convert_end(convertFile):" + convertFile);
            return Optional.of(convertFile);
        }

        log.info("S3Uploader_convert_end: End with failure(with creating new file)");
        return Optional.empty();
    }
}