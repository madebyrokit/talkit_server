package com.talkit.repository;


import com.talkit.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface PostRepository extends JpaRepository<Post, Long> , PostRepositoryCustom{
    List<Post> findByMemberId(Long memberId);
}