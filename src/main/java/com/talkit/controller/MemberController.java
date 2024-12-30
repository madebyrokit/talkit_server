package com.talkit.controller;


import com.talkit.dto.MemberDto;
import com.talkit.service.FileService;
import com.talkit.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URLConnection;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    private final FileService fileService;

    @PostMapping("/profileimage")
    public ResponseEntity<?> UploadProfileImage(@RequestParam("file") MultipartFile multipartFile, Authentication authentication) {
        log.info(multipartFile.getName());
        log.info(authentication.getName());
        fileService.upload(authentication.getName(), multipartFile);
        return ResponseEntity.ok().body("UPLOADED");
    }

    @GetMapping("/{filename}")
    public ResponseEntity<UrlResource> serveProfileImage(@PathVariable String filename) {
        try {
            Path filePath = Paths.get("/Users/mac/Documents/file/").resolve(filename).normalize();
            UrlResource resource = new UrlResource(filePath.toUri());

            if (!resource.exists()) {
                return ResponseEntity.notFound().build();
            }

            String contentType = URLConnection.guessContentTypeFromName(filename);
            if (contentType == null) {
                contentType = "application/octet-stream";
            }

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_TYPE, contentType)
                    .header(HttpHeaders.CONTENT_DISPOSITION,
                            "inline; filename=\"" + resource.getFilename() + "\"")
                    .body(resource);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/info")
    public ResponseEntity<MemberDto.Response> getMemberInfo(Authentication authentication) {
log.info("컨트롤러 {}",authentication.getName());
        return ResponseEntity.ok().body(memberService.getMemberInfo(authentication.getName()));
    }

    @DeleteMapping
    public ResponseEntity<?> deleteMember(Authentication authentication) {
        memberService.deleteMember(authentication.getName());
        return ResponseEntity.ok().body("member is deleted");
    }

    @PutMapping
    public ResponseEntity<?> updateMember(@RequestBody MemberDto.Request request, Authentication authentication) {
        memberService.updateMember(request, authentication.getName());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/posts")
    public ResponseEntity<List<MemberDto.ResponsePostList>> getPostListByMember(Authentication authentication) {
        return ResponseEntity.ok().body(memberService.getPostListByMember(authentication.getName()));
    }
    @GetMapping("/comments")
    public ResponseEntity<List<MemberDto.ResponseCommentList>> getCommentListByMember(Authentication authentication) {
        return ResponseEntity.ok().body(memberService.getCommentListByMember(authentication.getName()));
    }
    @GetMapping("/posts/liked")
    public ResponseEntity<List<MemberDto.ResponseLikedPostList>> getLikedPostListByMember(Authentication authentication) {
        return ResponseEntity.ok().body(memberService.getLikedPostListByMember(authentication.getName()));
    }
    @GetMapping("/comments/liked")
    public ResponseEntity<List<MemberDto.ResponseLikedCommentList>> getCLikedCommentPostListByMember(Authentication authentication) {
        return ResponseEntity.ok().body(memberService.getLikedCommentListByMember(authentication.getName()));
    }

}
