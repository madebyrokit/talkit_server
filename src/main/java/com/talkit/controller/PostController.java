package com.talkit.controller;

import com.talkit.dto.PostDto;

import com.talkit.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<PostDto.CreateResponse> createPost(@RequestBody PostDto.CreateRequest createRequest, Authentication authentication) {
        String userEmail = authentication.getName();
        PostDto.CreateResponse createResponse = postService.createPost(createRequest, userEmail);
        return ResponseEntity.ok().body(createResponse);
    }

    @GetMapping
    public ResponseEntity<PostDto.Response> getPost(@RequestParam Long postid) {
        PostDto.Response postResponse = postService.getPost(postid);
        return ResponseEntity.ok(postResponse);
    }

    @GetMapping("/list")
    public ResponseEntity<List<PostDto.ListResponse>> getPostList(@RequestParam int page, @RequestParam int size, @RequestParam String sort) {
        List<PostDto.ListResponse> listResponses = postService.getPostList(page, size, sort);
        return ResponseEntity.ok().body(listResponses);
    }

    @PutMapping
    public ResponseEntity<PostDto.UpdateResponse> updatePost(@RequestBody PostDto.UpdateRequest updatePostRequest, Authentication authentication) {
        String userEmail = authentication.getName();
        PostDto.UpdateResponse updateResponse = postService.updatePost(updatePostRequest, userEmail);
        return ResponseEntity.ok().body(updateResponse);
    }

    @DeleteMapping
    public ResponseEntity<?> deletePost(@RequestParam Long postid, Authentication authentication) {
        String userEmail = authentication.getName();
        postService.deletePost(postid, userEmail);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/L")
    public ResponseEntity<Long> toggleLike(@RequestBody PostDto.LikePostRequest likePostRequest, Authentication authentication) {
        String userEmail = authentication.getName();
        Long countLiked = postService.toggleToLikePost(likePostRequest, userEmail);
        return ResponseEntity.ok().body(countLiked);
    }

    @GetMapping("/barchart")
    public ResponseEntity<PostDto.logicResponse> barchart() {
        PostDto.logicResponse logicResponse = postService.barchart();
        return ResponseEntity.ok().body(logicResponse);
    }
}