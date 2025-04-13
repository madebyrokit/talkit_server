package com.talkit.controller;

import com.talkit.dto.MemberDto;
import com.talkit.service.FileService;
import com.talkit.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
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

    @GetMapping
    public ResponseEntity<MemberDto.Response> getMemberInfo(Authentication authentication) {
        return ResponseEntity.ok().body(memberService.getMemberInfo(authentication.getName()));
    }

    @GetMapping("/{filename}")
    public ResponseEntity<UrlResource> getAvatar(@PathVariable String filename) throws MalformedURLException {
        Path filePath = Paths.get("/Users/mac/Documents/file/").resolve(filename).normalize();
        UrlResource resource = new UrlResource(filePath.toUri());

        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(resource);
    }

    @PostMapping("/avatar")
    public ResponseEntity<?> uploadAvatar(@RequestParam("file") MultipartFile multipartFile, Authentication authentication) {
        log.info(multipartFile.getName());
        log.info(authentication.getName());
        fileService.upload(authentication.getName(), multipartFile);
        return ResponseEntity.ok().body("avatar is uploaded");
    }

    @PutMapping
    public ResponseEntity<?> updateMember(@RequestBody MemberDto.Request request, Authentication authentication) {
        return ResponseEntity.ok().body(memberService.updateMember(request, authentication.getName()));
    }

    @DeleteMapping
    public ResponseEntity<?> deleteMember(Authentication authentication) {
        memberService.deleteMember(authentication.getName());
        return ResponseEntity.ok().body("member is deleted");
    }

    // detail
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
