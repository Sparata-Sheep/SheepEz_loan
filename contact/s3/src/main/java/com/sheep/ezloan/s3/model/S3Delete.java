package com.sheep.ezloan.s3.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class S3Delete {

    private String result;

    private String originalFileName;

    private String deletedUploadFileUrl;

}
