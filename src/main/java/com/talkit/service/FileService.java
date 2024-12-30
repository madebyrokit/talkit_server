package com.talkit.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    void upload(String userEmail, MultipartFile multipartFile);
    String storeFile(MultipartFile multipartFile);
}
