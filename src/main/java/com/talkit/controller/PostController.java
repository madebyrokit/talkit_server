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

    @GetMapping
    public ResponseEntity<PostDto.Response> getPost(@RequestParam Long post_id) {
        PostDto.Response postResponse = postService.getPost(post_id);
        return ResponseEntity.ok(postResponse);
    }

    @PutMapping
    public ResponseEntity<PostDto.Updated> updatePost(@RequestParam Long post_id, @RequestBody PostDto.Updated updatePostRequest, Authentication authentication) {
        PostDto.Updated updatedPost = postService.updatePost(post_id, updatePostRequest, authentication.getName());
        return ResponseEntity.ok().body(updatedPost);
    }

    @DeleteMapping
    public ResponseEntity<?> deletePost(@RequestParam Long post_id, Authentication authentication) {
        postService.deletePost(post_id, authentication.getName());
        return ResponseEntity.ok().body("OK");
    }

    @PostMapping("/liked")
    public ResponseEntity<Long> switchLike(@RequestParam Long post_id, Authentication authentication) {
        return ResponseEntity.ok().body(postService.switchLike(post_id, authentication.getName()));
    }

    @GetMapping("/barchart")
    public ResponseEntity<PostDto.logicResponse> barchart() {
        return ResponseEntity.ok().body(postService.barchart());
    }
}