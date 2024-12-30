package com.talkit.repository;

import com.talkit.entity.LikeComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LikeCommentRepository extends JpaRepository<LikeComment, Long> {
    Long countByCommentId(Long commentId);
    Optional<LikeComment> findByMember_IdAndCommentId(Long memberId, Long commentId);
    List<LikeComment> findByMemberId(Long memberId);
}
