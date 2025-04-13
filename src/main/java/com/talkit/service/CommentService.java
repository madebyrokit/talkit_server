package com.talkit.service;

import com.talkit.dto.CommentDto;
import java.util.List;


public interface CommentService {
    CommentDto.CreateResponse createComment(CommentDto.CreateRequest create, String userEmail);
    List<CommentDto.Response> getCommentList(int page, int size, Long postId);
    CommentDto.Update updateComment(CommentDto.Update updateCommentRequest, String userEmail);
    void deleteComment(Long commentId, String userEmail);
    Long switchLike(Long commentId, String userEmail);
}
