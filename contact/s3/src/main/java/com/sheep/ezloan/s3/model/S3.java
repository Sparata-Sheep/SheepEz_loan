package com.sheep.ezloan.s3.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Getter
@AllArgsConstructor
@Builder
public class S3 {

    private UUID linkedUUID;

    private MultipartFile multipartFile;

}
