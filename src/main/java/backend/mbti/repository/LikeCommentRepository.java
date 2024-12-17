package backend.mbti.repository;

import backend.mbti.entity.LikeComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LikeCommentRepository extends JpaRepository<LikeComment, Long> {
    Long countByCommentId(Long commentId);
    Optional<LikeComment> findByMemberIdAndCommentId(Long memberId, Long commentId);
}
