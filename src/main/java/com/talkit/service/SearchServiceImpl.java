package com.talkit.service;

import com.talkit.dto.PostDto;
import com.talkit.entity.Post;
import com.talkit.repository.CommentRepository;
import com.talkit.repository.LikePostRepository;
import com.talkit.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class SearchServiceImpl implements SearchService{

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final LikePostRepository likePostRepository;

    @Override
    public List<PostDto.ListResponse> getPostListByKeyword(int page, int size, String keyword) {
        log.info("{} s {} s {}", page,size, keyword);
        PageRequest pageRequest = PageRequest.of(page, size);
        List<Post> postList = postRepository.getPostByKeyword(pageRequest, keyword);

        List<PostDto.ListResponse> listResponses = new ArrayList<>();

        if (postList != null) {
            for (Post post : postList) {
                PostDto.ListResponse response = new PostDto.ListResponse(
                        post.getId(),
                        post.getMember().getId(),
                        post.getMember().getUsername(),
                        post.getMember().getMbtitype(),
                        post.getMember().getProfile_image().getStoreFileName(),
                        post.getTitle(),
                        post.getOptionA(),
                        post.getOptionB(),
                        commentRepository.countByPostId(post.getId()),
                        post.getCreatedAt(),
                        post.getView(),
                        likePostRepository.countByPostId(post.getId())
                );
                listResponses.add(response);
            }
            return listResponses;
        }
        return null;
    }
}
