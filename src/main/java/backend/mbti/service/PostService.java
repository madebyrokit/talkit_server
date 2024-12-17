package backend.mbti.service;


import backend.mbti.dto.PostDto;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PostService {
    PostDto.CreateResponse createPost(PostDto.CreateRequest createRequest, String username);
    Page<PostDto.ListResponse> getListByPost(int page, int size);
    PostDto.Response getPost(Long postId);
    void updatePost(PostDto.UpdateRequest updatePostRequest, String username);
    void deletePost(PostDto.DeleteRequest deletePostRequest, String username);
    Long toggleToLikePost(PostDto.LikePostRequest likePostRequest, String username);
    PostDto.logicResponse barchart();
}