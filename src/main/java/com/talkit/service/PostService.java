package com.talkit.service;


import com.talkit.dto.PostDto;
import com.talkit.entity.LikePost;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PostService {
    PostDto.PostResponse createPost(PostDto.CreatedPostRequest createRequest, String userEmail);
    List<PostDto.PostResponse> getPostList(int page, int size, String sort);
    PostDto.Response getPost(Long postId);
    PostDto.Updated updatePost(Long post_id, PostDto.Updated updatePostRequest, String userEmail);
    void deletePost(Long postId, String userEmail);
    Long switchLike(Long post_id, String userEmail);
    PostDto.logicResponse barchart();
}