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

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class PostServiceImpl implements PostService{

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
                createRequest.getOpinionA(),
                createRequest.getOpinionB(),
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
                            commentRepository.countByPostId(post.getId()),
                            post.getTotal_view(),
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
                            commentRepository.countByPostId(post.getId()),
                            post.getTotal_view(),
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
                            commentRepository.countByPostId(post.getId()),
                            post.getTotal_view(),
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
                            commentRepository.countByPostId(post.getId()),
                            post.getTotal_view(),
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

            Comment getTopCommentA = commentRepository.findTopCommentA(optionalPost.get().getId());
            CommentDto.getTopCommentA getTopCommentADto;
            if (getTopCommentA != null) {

                getTopCommentADto = new CommentDto.getTopCommentA();
                getTopCommentADto.setCommentId(getTopCommentA.getId());
                getTopCommentADto.setUsername(getTopCommentA.getMember().getUsername());
                getTopCommentADto.setMbtiType(getTopCommentA.getMember().getMbtitype());
                getTopCommentADto.setProfileImage(getTopCommentA.getMember().getAvatar().getStoreFileName());
                getTopCommentADto.setComment(getTopCommentA.getContent());
                getTopCommentADto.setSelectedOption(getTopCommentA.getSelectOption());
                getTopCommentADto.setCountLikeComment(likeCommentRepository.countByCommentId(getTopCommentA.getId()));
                getTopCommentADto.setCreatedAtPost(getTopCommentA.getPost().getCreated_at());
                getTopCommentADto.setCreatedAtComment(getTopCommentA.getCreatedAt());

            } else {
                getTopCommentADto = null;
            }

            Comment getTopCommentB = commentRepository.findTopCommentB(optionalPost.get().getId());
            CommentDto.getTopCommentB getTopCommentBDto;
            if (getTopCommentB != null) {

                getTopCommentBDto = new CommentDto.getTopCommentB();
                getTopCommentBDto.setCommentId(getTopCommentB.getId());
                getTopCommentBDto.setUsername(getTopCommentB.getMember().getUsername());
                getTopCommentBDto.setMbtiType(getTopCommentB.getMember().getMbtitype());
                getTopCommentBDto.setProfileImage(getTopCommentB.getMember().getAvatar().getStoreFileName());
                getTopCommentBDto.setComment(getTopCommentB.getContent());
                getTopCommentBDto.setSelectedOption(getTopCommentB.getSelectOption());
                getTopCommentBDto.setCountLikeComment(likeCommentRepository.countByCommentId(getTopCommentB.getId()));
                getTopCommentBDto.setCreatedAtPost(getTopCommentB.getPost().getCreated_at());
                getTopCommentBDto.setCreatedAtComment(getTopCommentB.getCreatedAt());

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
                    commentRepository.countByPostIdAndSelectOption(post.getId(), "A"),
                    commentRepository.countByPostIdAndSelectOption(post.getId(), "B"),
                    getTopCommentADto,
                    getTopCommentBDto);
        } else {
            return null;
        }
    }


    @Override
    public PostDto.UpdateResponse updatePost(PostDto.UpdateRequest updatePostRequest, String username) {
        Post post = postRepository.findById(updatePostRequest.getPost_id()).orElseThrow(()->
                new AppException("게시글을 찾을 수 없습니다.", HttpStatus.NOT_FOUND)
        );
        post.setTitle(updatePostRequest.getTitle());
        post.setOption_a(updatePostRequest.getOpinion_a());
        post.setOption_b(updatePostRequest.getOpinion_b());

        postRepository.save(post);
        return new PostDto.UpdateResponse(post.getId(), post.getTitle(), post.getOption_a(), post.getOption_b());
    }

    @Override
    public void deletePost(Long postId, String username) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new AppException("게시글이 존재하지 않습니다.", HttpStatus.NOT_FOUND));

        postRepository.delete(post);
    }

    @Override
    public Long toggleToLikePost(PostDto.LikePostRequest likePostRequest, String userEmail) {
        Member member = memberRepository.findByEmail(userEmail)
                .orElseThrow(() -> new AppException("맴버가 존재하지 않습니다.", HttpStatus.NOT_FOUND));

        Post post = postRepository.findById(likePostRequest.getPost_id())
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

        Long getCommentCountByOptionA = commentRepository.countByPostIdAndSelectOption(randomPost.getId(), "A");
        Long getCommentCountByOptionB = commentRepository.countByPostIdAndSelectOption(randomPost.getId(), "B");

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