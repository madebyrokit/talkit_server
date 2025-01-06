package com.talkit.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    String upload(String userEmail, MultipartFile multipartFile);
    String storeFile(MultipartFile multipartFile);
    Resource getAvatar(String fileName);
}
