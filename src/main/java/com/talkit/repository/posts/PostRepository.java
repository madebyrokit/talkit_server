package com.talkit.repository.posts;


import com.talkit.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface PostRepository extends JpaRepository<Post, Long> , PostRepositoryCustom{
    List<Post> findByMemberId(Long memberId);
}