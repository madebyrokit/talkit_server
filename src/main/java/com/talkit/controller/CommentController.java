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
    public ResponseEntity<List<CommentDto.Response>> getCommentList(@RequestParam int page, @RequestParam int size, @RequestParam Long post_id) {
        List<CommentDto.Response> commentList = commentService.getCommentList(page, size, post_id);
        return ResponseEntity.ok().body(commentList);
    }

    @PutMapping
    public ResponseEntity<CommentDto.Update> updateComment(@RequestBody CommentDto.Update updateRequest, Authentication authentication) {
        String email = authentication.getName();
        CommentDto.Update comments = commentService.updateComment(updateRequest, email);
        return ResponseEntity.ok().body(comments);
    }

    @DeleteMapping
    public ResponseEntity<String> deleteComment(@RequestParam Long comment_id, Authentication authentication) {
        String email = authentication.getName();
        commentService.deleteComment(comment_id, email);
        return ResponseEntity.ok().body("OK");
    }

    @PostMapping("/liked")
    public ResponseEntity<Long> switchLike(@RequestParam Long comment_id, Authentication authentication) {
        String userEmail = authentication.getName();
        Long countLiked = commentService.switchLike(comment_id, userEmail);
        return ResponseEntity.ok().body(countLiked);
    }
}
