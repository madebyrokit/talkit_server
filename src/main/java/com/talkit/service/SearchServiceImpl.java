package com.talkit.service;

import com.talkit.dto.PostDto;
import com.talkit.entity.Post;
import com.talkit.repository.Comments.CommentRepository;
import com.talkit.repository.LikePostRepository;
import com.talkit.repository.posts.PostRepository;
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
    public List<PostDto.PostResponse> getPostListByKeyword(int page, int size, String keyword) {
        PageRequest pageRequest = PageRequest.of(page, size);
        List<Post> postList = postRepository.getPostByKeyword(pageRequest, keyword);

        List<PostDto.PostResponse> listResponses = new ArrayList<>();

        if (postList != null) {
            for (Post post : postList) {
                PostDto.PostResponse response = new PostDto.PostResponse(
                        post.getId(),
                        post.getMember().getId(),
                        post.getMember().getUsername(),
                        post.getMember().getMbtitype(),
                        post.getMember().getAvatar().getStoreFileName(),
                        post.getTitle(),
                        post.getOption_a(),
                        post.getOption_b(),
                        post.getCreated_at(),
                        commentRepository.countByPostId(post.getId()),
                        post.getTotal_view(),
                        likePostRepository.countByPostId(post.getId())
                );
                listResponses.add(response);
            }
            return listResponses;
        }
        return null;
    }
}
