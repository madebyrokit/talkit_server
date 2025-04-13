package com.talkit.service;

import com.talkit.entity.Member;
import com.talkit.configuration.exception.AppException;
import com.talkit.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class FileServiceImpl implements FileService{
    private final MemberRepository memberRepository;

    @Value("${file.upload-dir}")
    private String filePath;


    @Override
    public String upload(String userEmail, MultipartFile multipartFile) {
        Member member = memberRepository.findByEmail(userEmail)
                .orElseThrow(() -> new AppException("맴버를 찾을 수 없습니다.", HttpStatus.NOT_FOUND));

        String fileName = storeFile(multipartFile);

        member.getAvatar().setOriginalFileName(multipartFile.getName());
        member.getAvatar().setStoreFileName(fileName);

        memberRepository.save(member);

        return fileName;
    }

    @Override
    public String storeFile(MultipartFile multipartFile) {
        String originalFilename = multipartFile.getOriginalFilename();
        String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
        String storeFileName = UUID.randomUUID() + fileExtension;

        try {
            multipartFile.transferTo(new File(filePath+storeFileName));
        } catch (IOException e) {
            throw new AppException("Avatar 저장 실패", HttpStatus.NOT_FOUND);
        }

        return storeFileName;
    }

    @Override
    public Resource getAvatar(String fileName) {
        try {
            Path path = Paths.get(filePath).resolve(fileName).normalize();
            return new UrlResource(path.toUri());
        } catch (MalformedURLException e) {
            throw new AppException("파일을 찾을 수 없습니다.", HttpStatus.NOT_FOUND);
        }
    }
}
