package backend.mbti.controller;

import backend.mbti.dto.PostDto;

import backend.mbti.service.PostService;
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
@CrossOrigin(origins = "http://localhost:3000")
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<PostDto.CreateResponse> createPost(@RequestBody PostDto.CreateRequest createRequest, Authentication authentication) {
        return ResponseEntity.ok().body(postService.createPost(createRequest, authentication.getName()));
    }

    @GetMapping
    public ResponseEntity<Page<PostDto.ListResponse>> getListPost(@RequestParam int page, @RequestParam int size) {
        return ResponseEntity.ok().body(postService.getListByPost(page, size));
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostDto.Response> getPost(@PathVariable Long postId) {
        PostDto.Response postResponse = postService.getPost(postId);
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

    @GetMapping("barchart")
    public ResponseEntity<PostDto.logicResponse> barchart() {

        return ResponseEntity.ok().body(postService.barchart());
    }
}