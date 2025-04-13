package com.talkit.repository.Comments;

import com.talkit.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long>, CommentRepositoryCustom {
    List<Comment> findByMemberId(Long memberId);
    Long countByPostId(Long postId);
    Long countByPostIdAndOpinion(Long postId, String opinion);

}