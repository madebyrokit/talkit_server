package com.talkit.service;

import com.talkit.dto.CommentDto;
import java.util.List;


public interface CommentService {
    CommentDto.CreateResponse createComment(CommentDto.CreateRequest create, String username);
    List<CommentDto.GetResponse> getComments(Long postId);
    Boolean updateComment(CommentDto.UpdateRequest updateCommentRequest, String username);
    void deleteComment(CommentDto.DeleteRequest delete, String username);
    Long toggleLike(Long commentId, String username);
}
