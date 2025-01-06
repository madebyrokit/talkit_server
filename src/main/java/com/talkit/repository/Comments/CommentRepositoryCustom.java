package com.talkit.repository.Comments;

import com.talkit.entity.Comment;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface CommentRepositoryCustom {
    List<Comment> getCommentList(Pageable pageable, Long postId);
    Comment findTopCommentA(Long postId);
    Comment findTopCommentB(Long postId);
}
