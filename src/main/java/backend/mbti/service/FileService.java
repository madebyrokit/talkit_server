package backend.mbti.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    void upload(String username, MultipartFile multipartFile);
    String storeFile(MultipartFile multipartFile);
}
