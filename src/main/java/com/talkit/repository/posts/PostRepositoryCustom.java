package com.talkit.repository.posts;

import com.talkit.entity.Post;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface PostRepositoryCustom {
    List<Post> getPostListOrderByLikeCountDesc(Pageable pageable);

    List<Post> getPostListOrderByIdDesc(Pageable pageable);

    List<Post> getPostListOrderByViewDesc(Pageable pageable);

    List<Post> getPostListOrderByCommentDesc(Pageable pageable);

    Post getRamdomPost();

    List<Post> getPostByKeyword(PageRequest pageRequest, String keyword);
}
