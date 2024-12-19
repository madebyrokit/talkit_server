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
@RequestMapping("/post")
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<PostDto.CreateResponse> createPost(@RequestBody PostDto.CreateRequest createRequest, Authentication authentication) {
        return ResponseEntity.ok().body(postService.createPost(createRequest, authentication.getName()));
    }

    @GetMapping("/list")
    public ResponseEntity<List<PostDto.ListResponse>> getPostList(@RequestParam int page, @RequestParam int size, @RequestParam String sort) {
        return ResponseEntity.ok().body(postService.getPostList(page, size, sort));
    }

    @GetMapping()
    public ResponseEntity<PostDto.Response> getPost(@RequestParam int page, @RequestParam int size, @RequestParam Long post_id) {
        PostDto.Response postResponse = postService.getPost(page, size, post_id);
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
    public void deletePost(@RequestBody PostDto.DeleteRequest deletePostRequest, Authentication authentication) {
        postService.deletePost(deletePostRequest, authentication.getName());
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