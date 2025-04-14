package com.talkit.service;

import com.talkit.dto.CommentDto;
import com.talkit.dto.PostDto;

import com.talkit.configuration.exception.AppException;
import com.talkit.entity.Comment;
import com.talkit.entity.LikePost;
import com.talkit.entity.Member;
import com.talkit.entity.Post;
import com.talkit.repository.*;
import com.talkit.repository.Comments.CommentRepository;
import com.talkit.repository.posts.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final MemberRepository memberRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final LikePostRepository likePostRepository;
    private final LikeCommentRepository likeCommentRepository;

    @Override
    public PostDto.PostResponse createPost(PostDto.CreatedPostRequest createRequest, String userEmail) {
        Member member = memberRepository.findByEmail(userEmail)
                .orElseThrow(() -> new AppException("맴버를 찾을 수 없습니다.", HttpStatus.NOT_FOUND));

        Post post = new Post(
                null,
                createRequest.getTitle(),
                createRequest.getOpinion_a(),
                createRequest.getOpinion_b(),
                member,
                new Date(),
                0L,
                null,
                null
        );

        Post newPost = postRepository.save(post);


        return new PostDto.PostResponse(
                newPost.getId(),
                newPost.getMember().getId(),
                newPost.getMember().getUsername(),
                newPost.getMember().getMbtitype(),
                newPost.getMember().getAvatar().getStoreFileName(),
                newPost.getTitle(),
                newPost.getOption_a(),
                newPost.getOption_b(),
                newPost.getCreated_at(),
                commentRepository.countByPostId(newPost.getId()),
                newPost.getTotal_view(),
                likePostRepository.countByPostId(newPost.getId())
        );
    }

    @Override
    public List<PostDto.PostResponse> getPostList(int page, int size, String sort) {
        PageRequest pageable = PageRequest.of(page, size, Sort.by(Sort.Order.desc("id")));

        if (sort.equals("like_desc")) {
            List<Post> postPage = postRepository.getPostListOrderByLikeCountDesc(pageable);

            return postPage.stream()
                    .map(post -> new PostDto.PostResponse(
                            post.getId(),
                            post.getMember().getId(),
                            post.getMember().getUsername(),
                            post.getMember().getMbtitype(),
                            post.getMember().getAvatar().getStoreFileName(),
                            post.getTitle(),
                            post.getOption_a(),
                            post.getOption_b(),
                            post.getCreated_at(),
                            post.getTotal_view(),
                            commentRepository.countByPostId(post.getId()),
                            likePostRepository.countByPostId(post.getId())
                    ))
                    .collect(Collectors.toList());

        } else if (sort.equals("view_desc")) {
            List<Post> postPage = postRepository.getPostListOrderByViewDesc(pageable);
            return postPage.stream()
                    .map(post -> new PostDto.PostResponse(
                            post.getId(),
                            post.getMember().getId(),
                            post.getMember().getUsername(),
                            post.getMember().getMbtitype(),
                            post.getMember().getAvatar().getStoreFileName(),
                            post.getTitle(),
                            post.getOption_a(),
                            post.getOption_b(),
                            post.getCreated_at(),
                            post.getTotal_view(),
                            commentRepository.countByPostId(post.getId()),
                            likePostRepository.countByPostId(post.getId())
                    ))
                    .collect(Collectors.toList());
        } else if (sort.equals("last")) {
            List<Post> postPage = postRepository.getPostListOrderByIdDesc(pageable);
            return postPage.stream()
                    .map(post -> new PostDto.PostResponse(
                            post.getId(),
                            post.getMember().getId(),
                            post.getMember().getUsername(),
                            post.getMember().getMbtitype(),
                            post.getMember().getAvatar().getStoreFileName(),
                            post.getTitle(),
                            post.getOption_a(),
                            post.getOption_b(),
                            post.getCreated_at(),
                            post.getTotal_view(),
                            commentRepository.countByPostId(post.getId()),
                            likePostRepository.countByPostId(post.getId())
                    ))
                    .collect(Collectors.toList());
        } else if (sort.equals("comment_desc")) {
            List<Post> postPage = postRepository.getPostListOrderByCommentDesc(pageable);
            return postPage.stream()
                    .map(post -> new PostDto.PostResponse(
                            post.getId(),
                            post.getMember().getId(),
                            post.getMember().getUsername(),
                            post.getMember().getMbtitype(),
                            post.getMember().getAvatar().getStoreFileName(),
                            post.getTitle(),
                            post.getOption_a(),
                            post.getOption_b(),
                            post.getCreated_at(),
                            post.getTotal_view(),
                            commentRepository.countByPostId(post.getId()),
                            likePostRepository.countByPostId(post.getId())
                    ))
                    .collect(Collectors.toList());
        }
        return null;
    }


    @Override
    public PostDto.Response getPost(Long postId) {
        Optional<Post> optionalPost = postRepository.findById(postId);

        if (optionalPost.isPresent()) {
            Post post = optionalPost.get();
            post.incrementView();
            postRepository.save(post);

            Comment findTopCommentA = commentRepository.findTopCommentA(optionalPost.get().getId());
            CommentDto.TopCommentResponse getTopCommentADto;

            if (findTopCommentA != null) {
                getTopCommentADto = new CommentDto.TopCommentResponse(
                        findTopCommentA.getId(),
                        findTopCommentA.getMember().getUsername(),
                        findTopCommentA.getMember().getMbtitype(),
                        findTopCommentA.getMember().getAvatar().getStoreFileName(),
                        findTopCommentA.getContent(),
                        findTopCommentA.getOpinion(),
                        likeCommentRepository.countByCommentId(findTopCommentA.getId()),
                        findTopCommentA.getCreated_at()
                );


            } else {
                getTopCommentADto = null;
            }

            Comment findTopCommentB = commentRepository.findTopCommentB(optionalPost.get().getId());
            CommentDto.TopCommentResponse getTopCommentBDto;
            if (findTopCommentB != null) {
                getTopCommentBDto = new CommentDto.TopCommentResponse(
                        findTopCommentB.getId(),
                        findTopCommentB.getMember().getUsername(),
                        findTopCommentB.getMember().getMbtitype(),
                        findTopCommentB.getMember().getAvatar().getStoreFileName(),
                        findTopCommentB.getContent(),
                        findTopCommentB.getOpinion(),
                        likeCommentRepository.countByCommentId(findTopCommentB.getId()),
                        findTopCommentB.getCreated_at()
                );
            } else {
                getTopCommentBDto = null;
            }


            return new PostDto.Response(
                    post.getId(),
                    post.getMember().getId(),
                    post.getMember().getUsername(),
                    post.getMember().getMbtitype(),
                    post.getMember().getAvatar().getStoreFileName(),
                    post.getTitle(),
                    post.getOption_a(),
                    post.getOption_b(),
                    post.getCreated_at(),
                    post.getTotal_view(),
                    likePostRepository.countByPostId(post.getId()),
                    commentRepository.countByPostId(post.getId()),
                    commentRepository.countByPostIdAndOpinion(post.getId(), "A"),
                    commentRepository.countByPostIdAndOpinion(post.getId(), "B"),
                    getTopCommentADto,
                    getTopCommentBDto);
        } else {
            return null;
        }
    }


    @Override
    public PostDto.Updated updatePost(Long post_id, PostDto.Updated updatePostRequest, String username) {
        Post post = postRepository.findById(post_id).orElseThrow(() ->
                new AppException("the post can not be found", HttpStatus.NOT_FOUND)
        );

        post.setTitle(updatePostRequest.getTitle());
        post.setOption_a(updatePostRequest.getOpinion_a());
        post.setOption_b(updatePostRequest.getOpinion_b());

        postRepository.save(post);
        return new PostDto.Updated(post.getId(), post.getTitle(), post.getOption_a(), post.getOption_b());
    }

    @Override
    public void deletePost(Long postId, String username) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new AppException("게시글이 존재하지 않습니다.", HttpStatus.NOT_FOUND));

        postRepository.delete(post);
    }

    @Override
    public Long switchLike(@RequestParam Long post_id, String userEmail) {
        Member member = memberRepository.findByEmail(userEmail)
                .orElseThrow(() -> new AppException("맴버가 존재하지 않습니다.", HttpStatus.NOT_FOUND));

        Post post = postRepository.findById(post_id)
                .orElseThrow(() -> new AppException("게시글을 찾을 수 없습니다.", HttpStatus.NOT_FOUND));

        Optional<LikePost> optionalLikePost = likePostRepository.findByMember_IdAndPostId(member.getId(), post.getId());

        if (optionalLikePost.isPresent()) {
            likePostRepository.delete(optionalLikePost.get());
            return likePostRepository.countByPostId(post.getId());
        } else {
            LikePost likePost1 = new LikePost(post, member);
            likePostRepository.save(likePost1);
            post.getLikePost().add(likePost1);
            postRepository.save(post);
            return likePostRepository.countByPostId(post.getId());
        }
    }

    @Override
    public PostDto.logicResponse barchart() {
        Post randomPost = postRepository.getRamdomPost();

        Long getCommentCountByOptionA = commentRepository.countByPostIdAndOpinion(randomPost.getId(), "A");
        Long getCommentCountByOptionB = commentRepository.countByPostIdAndOpinion(randomPost.getId(), "B");

        PostDto.logicResponse logicResponse = new PostDto.logicResponse();
        logicResponse.setPost_id(randomPost.getId());
        logicResponse.setTitle(randomPost.getTitle());
        logicResponse.setOpinion_a(randomPost.getOption_a());
        logicResponse.setOpinion_b(randomPost.getOption_b());
        logicResponse.setTotal_opinion_a(getCommentCountByOptionA);
        logicResponse.setTotal_opinion_b(getCommentCountByOptionB);

        return logicResponse;
    }

}