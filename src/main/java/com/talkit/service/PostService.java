package com.talkit.service;


import com.talkit.dto.PostDto;
import com.talkit.entity.LikePost;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PostService {
    PostDto.CreateResponse createPost(PostDto.CreateRequest createRequest, String username);
    List<PostDto.ListResponse> getPostList(int page, int size, String sort);
    PostDto.Response getPost(Long postId);
    void updatePost(PostDto.UpdateRequest updatePostRequest, String username);
    void deletePost(PostDto.DeleteRequest deletePostRequest, String username);
    Long toggleToLikePost(PostDto.LikePostRequest likePostRequest, String username);
    PostDto.logicResponse barchart();

    List<PostDto.ListResponse> getPostListByKeyword(String keyword);
}