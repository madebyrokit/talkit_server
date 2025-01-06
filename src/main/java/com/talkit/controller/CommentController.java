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
@RequestMapping("/comment")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<CommentDto.CreateResponse> createComment(@RequestBody CommentDto.CreateRequest createRequest, Authentication authentication) {
        String userEmail = authentication.getName();
        CommentDto.CreateResponse createResponse = commentService.createComment(createRequest, userEmail);
        return ResponseEntity.ok().body(createResponse);
    }

    @GetMapping("/list")// repair
    public ResponseEntity<List<CommentDto.GetResponse>> getCommentList(@RequestParam int page, @RequestParam int size, @RequestParam Long postid) {
        List<CommentDto.GetResponse> comments = commentService.getCommentList(page, size, postid);
        return ResponseEntity.ok(comments);
    }

    @PutMapping
    public ResponseEntity<CommentDto.UpdateResponse> updateComment(@RequestBody CommentDto.UpdateRequest updateRequest, Authentication authentication) {
        String userEmail = authentication.getName();
        CommentDto.UpdateResponse comments = commentService.updateComment(updateRequest, userEmail);
        return ResponseEntity.ok().body(comments);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteComment(@RequestParam Long commentid, Authentication authentication) {
        String userEmail = authentication.getName();
        commentService.deleteComment(commentid, userEmail);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/L")
    public ResponseEntity<Long> toggleLikeByComment(@RequestBody CommentDto.LikeRequest likeRequest, Authentication authentication) {
        String userEmail = authentication.getName();
        Long countLiked = commentService.toggleLike(likeRequest.getCommentId(), userEmail);
        return ResponseEntity.ok().body(countLiked);
    }
}
