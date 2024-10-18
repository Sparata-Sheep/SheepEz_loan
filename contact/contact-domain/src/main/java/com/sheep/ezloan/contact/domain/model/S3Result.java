package com.sheep.ezloan.contact.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class S3Result {

    private String originalFileName;

    private String uploadFileName;

    private String uploadFilePath;

    private String uploadFileUrl;

}
