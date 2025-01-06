package com.talkit.controller;

import com.talkit.dto.MemberDto;
import com.talkit.service.FileService;
import com.talkit.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    private final FileService fileService;

    @Value("${file.upload-dir}")
    private String fileUploadPath;

    @PostMapping("/avatar")
    public ResponseEntity<String> UploadProfileImage(@RequestParam("file") MultipartFile multipartFile, Authentication authentication) {
        String userEmail = authentication.getName();
        String storedFilePath = fileService.upload(userEmail, multipartFile);
        return ResponseEntity.ok().body(storedFilePath);
    }

    @GetMapping("/{avatar}")
    public ResponseEntity<Resource> getImage(@PathVariable String avatar) {
        Resource resource = fileService.getAvatar(avatar);
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(resource);
    }

    @GetMapping("/info")
    public ResponseEntity<MemberDto.Response> getMemberInfo(Authentication authentication) {
        String userEmail = authentication.getName();
        MemberDto.Response response = memberService.getMemberInfo(userEmail);
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteMember(Authentication authentication) {
        String userEmail = authentication.getName();
        memberService.deleteMember(userEmail);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity<MemberDto.Response> updateMember(@RequestBody MemberDto.Request request, Authentication authentication) {
        String userEmail = authentication.getName();
        MemberDto.Response response = memberService.updateMember(request, userEmail);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/posts")
    public ResponseEntity<List<MemberDto.ResponsePostList>> getPostListByMember(Authentication authentication) {
        String userEmail = authentication.getName();
        List<MemberDto.ResponsePostList> responsePostLists = memberService.getPostListByMember(userEmail);
        return ResponseEntity.ok().body(responsePostLists);
    }

    @GetMapping("/comments")
    public ResponseEntity<List<MemberDto.ResponseCommentList>> getCommentListByMember(Authentication authentication) {
        String userEmail = authentication.getName();
        List<MemberDto.ResponseCommentList> responseCommentList = memberService.getCommentListByMember(userEmail);
        return ResponseEntity.ok().body(responseCommentList);
    }

    @GetMapping("/posts/L")
    public ResponseEntity<List<MemberDto.ResponseLikedPostList>> getLikedPostListByMember(Authentication authentication) {
        String userEmail = authentication.getName();
        List<MemberDto.ResponseLikedPostList> responseLikedPostLists = memberService.getLikedPostListByMember(userEmail);
        return ResponseEntity.ok().body(responseLikedPostLists);
    }

    @GetMapping("/comments/L")
    public ResponseEntity<List<MemberDto.ResponseLikedCommentList>> getCLikedCommentPostListByMember(Authentication authentication) {
        String userEmail = authentication.getName();
        List<MemberDto.ResponseLikedCommentList> responseLikedCommentLists = memberService.getLikedCommentListByMember(userEmail);
        return ResponseEntity.ok().body(responseLikedCommentLists);
    }

}
