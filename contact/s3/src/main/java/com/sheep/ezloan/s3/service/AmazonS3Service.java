package com.sheep.ezloan.s3.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.sheep.ezloan.contact.domain.fileManager.FileManagerInterface;
import com.sheep.ezloan.contact.domain.model.S3Result;
import com.sheep.ezloan.s3.model.S3;
import com.sheep.ezloan.s3.model.S3Delete;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RequiredArgsConstructor
@Service
public class AmazonS3Service implements FileManagerInterface {

    private final AmazonS3 amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;

    /**
     * S3로 파일 업로드
     */
    public List<S3Result> uploadFiles(UUID linkedUUID, List<MultipartFile> multipartFiles) {

        List<S3Result> results = new ArrayList<>();

        for (MultipartFile multipartFile : multipartFiles) {
            results.add(uploadFile(linkedUUID.toString(), multipartFile));
        }

        return results;
    }

    public S3Result uploadFile(UUID linkedUUID, MultipartFile file) {

        return uploadFile(linkedUUID.toString(), file);
    }

    public S3Result uploadFile(S3 dto) {

        return uploadFile(dto.getLinkedUUID(), dto.getMultipartFile());
    }

    private S3Result uploadFile(String uploadFilePath, MultipartFile multipartFile) {
        String originalFileName = multipartFile.getOriginalFilename();
        String uploadFileName = getUuidFileName(originalFileName);
        String uploadFileUrl = "";

        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(multipartFile.getSize());
        objectMetadata.setContentType(multipartFile.getContentType());

        try (InputStream inputStream = multipartFile.getInputStream()) {

            String keyName = uploadFilePath + "/" + uploadFileName; // ex) 구분/년/월/일/파일.확장자

            // S3에 폴더 및 파일 업로드
            amazonS3Client.putObject(new PutObjectRequest(bucketName, keyName, inputStream, objectMetadata));

            // S3에 업로드한 폴더 및 파일 URL
            uploadFileUrl = amazonS3Client.getUrl(bucketName, keyName).toString();

        }
        catch (IOException e) {
            e.printStackTrace();
            log.error("Filed upload failed", e);
        }

        return S3Result.builder()
            .originalFileName(originalFileName)
            .uploadFilePath(uploadFilePath)
            .uploadFileName(uploadFileName)
            .uploadFileUrl(uploadFileUrl)
            .build();
    }

    /**
     * S3에 업로드된 파일 삭제
     */
    public S3Delete deleteFile(UUID likedUUID, String uploadFileName) {

        String result = "success";
        String uploadFileUrl = "";

        try {
            String keyName = likedUUID.toString() + "/" + uploadFileName; // ex)
                                                                          // UUID/파일.확장자
            boolean isObjectExist = amazonS3Client.doesObjectExist(bucketName, keyName);
            uploadFileUrl = amazonS3Client.getUrl(bucketName, keyName).toString();
            if (isObjectExist) {
                amazonS3Client.deleteObject(bucketName, keyName);
            }
            else {
                result = "file not found";
            }
        }
        catch (Exception e) {
            log.debug("Delete File failed", e);
            result = "failed";
        }

        return S3Delete.builder()
            .result(result)
            .originalFileName(uploadFileName)
            .deletedUploadFileUrl(uploadFileUrl)
            .build();
    }

    /**
     * UUID 파일명 반환
     */
    public String getUuidFileName(String fileName) {
        String ext = fileName.substring(fileName.indexOf(".") + 1);
        return UUID.randomUUID() + "." + ext;
    }

}