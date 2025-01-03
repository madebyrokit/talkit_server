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
        return ResponseEntity.ok().body(commentService.createComment(createRequest, authentication.getName()));
    }

    @GetMapping("/list")// repair
    public ResponseEntity<List<CommentDto.GetResponse>> getCommentList(@RequestParam int page, @RequestParam int size, @RequestParam Long postid) {
        List<CommentDto.GetResponse> comments = commentService.getCommentList(page, size, postid);
        return ResponseEntity.ok(comments);
    }

    @PutMapping
    public ResponseEntity<?> updateComment(@RequestBody CommentDto.UpdateRequest updateRequest, Authentication authentication) {
        Boolean comments = commentService.updateComment(updateRequest, authentication.getName());
        return ResponseEntity.ok().body(comments);
    }

    @DeleteMapping
    public void deleteComment(@RequestParam Long commentid, Authentication authentication) {
        commentService.deleteComment(commentid, authentication.getName());
    }

    @PostMapping("/like")
    public ResponseEntity<Long> toggleLikeComment(@RequestBody CommentDto.LikeRequest likeRequest, Authentication authentication) {
        return ResponseEntity.ok().body(commentService.toggleLike(likeRequest.getCommentId(), authentication.getName()));
    }
}
