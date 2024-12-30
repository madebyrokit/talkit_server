package com.talkit.repository;

import com.talkit.entity.LikePost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LikePostRepository extends JpaRepository<LikePost, Long> {
    Long countByPostId(Long postId);
    Optional<LikePost> findByMember_IdAndPostId(Long memberId, Long postId);
    List<LikePost> findByMemberId(Long memberId);
}
