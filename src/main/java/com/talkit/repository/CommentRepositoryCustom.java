package com.talkit.repository;

import com.talkit.entity.Comment;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;


public interface CommentRepositoryCustom {
    List<Comment> getCommentList(Pageable pageable, Long postId);
    Comment findTopCommentA(Long postId);
    Comment findTopCommentB(Long postId);
}
