package com.talkit.service;

import com.talkit.entity.Member;
import com.talkit.configuration.exception.AppException;
import com.talkit.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class FileServiceImpl implements FileService{
    private final MemberRepository memberRepository;

    @Value("${file.upload-dir}")
    private String filePath;
    public String getFullPath(String filename) {
        return filePath + filename;
    }

    @Override
    public void upload(String userEmail, MultipartFile multipartFile) {
        Member member = memberRepository.findByEmail(userEmail)
                .orElseThrow(() -> new AppException("맴버를 찾을 수 없습니다.", HttpStatus.NOT_FOUND));

        String fileName = storeFile(multipartFile);

        member.getProfile_image().setOriginalFileName(multipartFile.getName());
        member.getProfile_image().setStoreFileName(fileName);
        memberRepository.save(member);
    }

    @Override
    public String storeFile(MultipartFile multipartFile) {
        String originalFilename = multipartFile.getOriginalFilename();
        String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
        String storeFileName = UUID.randomUUID() + fileExtension;

        try {
            multipartFile.transferTo(new File(getFullPath(storeFileName)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return storeFileName;
    }
}
