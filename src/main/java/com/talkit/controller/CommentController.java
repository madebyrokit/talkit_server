package com.talkit.controller;

import com.talkit.dto.CommentDto;
import com.talkit.service.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<CommentDto.CreateResponse> createComment(@RequestBody CommentDto.CreateRequest createRequest, Authentication authentication) {
        String email = authentication.getName();
        CommentDto.CreateResponse createResponse = commentService.createComment(createRequest, email);
        return ResponseEntity.ok().body(createResponse);
    }

    @GetMapping
    public ResponseEntity<List<CommentDto.GetResponse>> getCommentList(@RequestParam int page, @RequestParam int size, @RequestParam Long postid) {
        List<CommentDto.GetResponse> commentList = commentService.getCommentList(page, size, postid);
        return ResponseEntity.ok().body(commentList);
    }

    @PutMapping
    public ResponseEntity<CommentDto.UpdateResponse> updateComment(@RequestBody CommentDto.UpdateRequest updateRequest, Authentication authentication) {
        String userEmail = authentication.getName();
        CommentDto.UpdateResponse comments = commentService.updateComment(updateRequest, userEmail);
        return ResponseEntity.ok().body(comments);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteComment(@RequestParam Long comment_id, Authentication authentication) {
        String userEmail = authentication.getName();
        commentService.deleteComment(comment_id, userEmail);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/liked")
    public ResponseEntity<Long> switchLike(@RequestBody CommentDto.LikeRequest likeRequest, Authentication authentication) {
        String userEmail = authentication.getName();
        Long countLiked = commentService.toggleLike(likeRequest.getCommentId(), userEmail);
        return ResponseEntity.ok().body(countLiked);
    }
}
