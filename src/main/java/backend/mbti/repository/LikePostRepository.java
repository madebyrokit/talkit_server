package backend.mbti.repository;

import backend.mbti.entity.LikePost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LikePostRepository extends JpaRepository<LikePost, Long> {
    Long countByPostId(Long postId);

    Optional<LikePost> findByMemberIdAndPostId(Long memberId, Long postId);
}
