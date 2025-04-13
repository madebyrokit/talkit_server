package com.talkit.controller;

import com.talkit.dto.PostDto;

import com.talkit.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<PostDto.PostResponse> createPost(@RequestBody PostDto.CreatedPostRequest createdPostRequest, Authentication authentication) {
        return ResponseEntity.ok().body(postService.createPost(createdPostRequest, authentication.getName()));
    }

    @GetMapping("/list")
    public ResponseEntity<List<PostDto.PostResponse>> getPostList(@RequestParam int page, @RequestParam int size, @RequestParam String sort) {
        return ResponseEntity.ok().body(postService.getPostList(page, size, sort));
    }

    @GetMapping()
    public ResponseEntity<PostDto.Response> getPost(@RequestParam Long postid) {
        PostDto.Response postResponse = postService.getPost(postid);
        if (postResponse != null) {
            return ResponseEntity.ok(postResponse);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping
    public void updatePost(@RequestBody PostDto.UpdateRequest updatePostRequest, Authentication authentication) {
        postService.updatePost(updatePostRequest, authentication.getName());
    }

    @DeleteMapping
    public ResponseEntity<?> deletePost(@RequestParam Long postid, Authentication authentication) {
        postService.deletePost(postid, authentication.getName());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/like")
    public ResponseEntity<Long> toggleLike(@RequestBody PostDto.LikePostRequest likePostRequest, Authentication authentication) {
        return ResponseEntity.ok().body(postService.toggleToLikePost(likePostRequest, authentication.getName()));
    }

    @GetMapping("/barchart")
    public ResponseEntity<PostDto.logicResponse> barchart() {

        return ResponseEntity.ok().body(postService.barchart());
    }
}