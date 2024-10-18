package com.sheep.ezloan.contact.domain.fileManager;

import com.sheep.ezloan.contact.domain.model.S3Result;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Component
public interface FileManagerInterface {

    S3Result uploadFile(UUID linkedUUID, MultipartFile file);

}
