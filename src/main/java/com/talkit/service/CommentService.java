package com.talkit.service;

import com.talkit.dto.CommentDto;
import java.util.List;


public interface CommentService {
    CommentDto.CreateResponse createComment(CommentDto.CreateRequest create, String userEmail);
    List<CommentDto.GetResponse> getCommentList(int page, int size, Long postId);
    CommentDto.UpdateResponse updateComment(CommentDto.UpdateRequest updateCommentRequest, String userEmail);
    void deleteComment(Long commentId, String userEmail);
    Long toggleLike(Long commentId, String userEmail);
}
