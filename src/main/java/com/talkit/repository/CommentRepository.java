package com.talkit.repository;

import com.talkit.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long>, CommentRepositoryCustom {
    List<Comment> findAllByPostId(Long postId);
    Long countByPostId(Long postId);
    Long countByPostIdAndSelectOption(Long postId, String selectOption);

}