package com.talkit.service;

import com.talkit.dto.CommentDto;
import com.talkit.dto.PostDto;

import com.talkit.configuration.exception.AppException;
import com.talkit.entity.Comment;
import com.talkit.entity.LikePost;
import com.talkit.entity.Member;
import com.talkit.entity.Post;
import com.talkit.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class PostServiceImpl implements PostService{

    private final MemberRepository memberRepository;
    private final PostRepository postRepository;
    private final LikePostRepository likePostRepository;
    private final LikeCommentRepository likeCommentRepository;
    private final CommentRepository commentRepository;

    @Override
    public PostDto.CreateResponse createPost(PostDto.CreateRequest createRequest, String username) {
        Member member = memberRepository.findByUsername(username)
                .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND));

        Post post = new Post(
                null,
                createRequest.getTitle(),
                createRequest.getOptionA(),
                createRequest.getOptionB(),
                member,
                new Date(),
                0L,
                null,
                null
        );

        Post newPost = postRepository.save(post);


        return new PostDto.CreateResponse(
                newPost.getId(),
                newPost.getMember().getId(),
                newPost.getMember().getUsername(),
                newPost.getMember().getMbtitype(),
                newPost.getMember().getProfile_image().getStoreFileName(),
                newPost.getTitle(),
                newPost.getOptionA(),
                newPost.getOptionB(),
                commentRepository.countByPostId(newPost.getId()),
                newPost.getCreatedAt(),
                newPost.getView(),
                likePostRepository.countByPostId(newPost.getId())
        );
    }

    @Override
    public List<PostDto.ListResponse> getPostList(int page, int size, String sort) {
        PageRequest pageable = PageRequest.of(page, size, Sort.by(Sort.Order.desc("id")));

        if (sort.equals("like_desc")) {
            log.info("like");
            List<Post> postPage = postRepository.getPostListOrderByLikeCountDesc(pageable);

            return postPage.stream()
                    .map(post -> new PostDto.ListResponse(
                            post.getId(),
                            post.getMember().getId(),
                            post.getMember().getUsername(),
                            post.getMember().getMbtitype(),
                            post.getMember().getProfile_image().getStoreFileName(),
                            post.getTitle(),
                            post.getOptionA(),
                            post.getOptionB(),
                            commentRepository.countByPostId(post.getId()),  // 댓글 수 카운팅
                            post.getCreatedAt(),
                            post.getView(),
                            likePostRepository.countByPostId(post.getId())  // 좋아요 수 카운팅
                    ))
                    .collect(Collectors.toList());

        } else if (sort.equals("view_desc")) {
            log.info("view");
            List<Post> postPage = postRepository.getPostListOrderByViewDesc(pageable);
            return postPage.stream()
                    .map(post -> new PostDto.ListResponse(
                            post.getId(),
                            post.getMember().getId(),
                            post.getMember().getUsername(),
                            post.getMember().getMbtitype(),
                            post.getMember().getProfile_image().getStoreFileName(),
                            post.getTitle(),
                            post.getOptionA(),
                            post.getOptionB(),
                            commentRepository.countByPostId(post.getId()),  // 댓글 수 카운팅
                            post.getCreatedAt(),
                            post.getView(),
                            likePostRepository.countByPostId(post.getId())  // 좋아요 수 카운팅
                    ))
                    .collect(Collectors.toList());
        } else if (sort.equals("last")) {
            log.info("last");
            List<Post> postPage = postRepository.getPostListOrderByIdDesc(pageable);
            return postPage.stream()
                    .map(post -> new PostDto.ListResponse(
                            post.getId(),
                            post.getMember().getId(),
                            post.getMember().getUsername(),
                            post.getMember().getMbtitype(),
                            post.getMember().getProfile_image().getStoreFileName(),
                            post.getTitle(),
                            post.getOptionA(),
                            post.getOptionB(),
                            commentRepository.countByPostId(post.getId()),  // 댓글 수 카운팅
                            post.getCreatedAt(),
                            post.getView(),
                            likePostRepository.countByPostId(post.getId())  // 좋아요 수 카운팅
                    ))
                    .collect(Collectors.toList());
        } else if (sort.equals("comment_desc")) {
            log.info("comment");
            List<Post> postPage = postRepository.getPostListOrderByCommentDesc(pageable);
            return postPage.stream()
                    .map(post -> new PostDto.ListResponse(
                            post.getId(),
                            post.getMember().getId(),
                            post.getMember().getUsername(),
                            post.getMember().getMbtitype(),
                            post.getMember().getProfile_image().getStoreFileName(),
                            post.getTitle(),
                            post.getOptionA(),
                            post.getOptionB(),
                            commentRepository.countByPostId(post.getId()),  // 댓글 수 카운팅
                            post.getCreatedAt(),
                            post.getView(),
                            likePostRepository.countByPostId(post.getId())  // 좋아요 수 카운팅
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
                getTopCommentADto.setProfileImage(getTopCommentA.getMember().getProfile_image().getStoreFileName());
                getTopCommentADto.setComment(getTopCommentA.getContent());
                getTopCommentADto.setSelectedOption(getTopCommentA.getSelectOption());
                getTopCommentADto.setCountLikeComment(likeCommentRepository.countByCommentId(getTopCommentA.getId()));
                getTopCommentADto.setCreateAtPost(getTopCommentA.getPost().getCreatedAt());
                getTopCommentADto.setCreateAtComment(getTopCommentA.getCreatedAt());

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
                getTopCommentBDto.setProfileImage(getTopCommentB.getMember().getProfile_image().getStoreFileName());
                getTopCommentBDto.setComment(getTopCommentB.getContent());
                getTopCommentBDto.setSelectedOption(getTopCommentB.getSelectOption());
                getTopCommentBDto.setCountLikeComment(likeCommentRepository.countByCommentId(getTopCommentB.getId()));
                getTopCommentBDto.setCreateAtPost(getTopCommentB.getPost().getCreatedAt());
                getTopCommentBDto.setCreateAtComment(getTopCommentB.getCreatedAt());

            } else {
                getTopCommentBDto = null;
            }


            return new PostDto.Response(
                    post.getId(),
                    post.getMember().getId(),
                    post.getMember().getUsername(),
                    post.getMember().getMbtitype(),
                    post.getMember().getProfile_image().getStoreFileName(),
                    post.getTitle(),
                    post.getOptionA(),
                    post.getOptionB(),
                    post.getCreatedAt(),
                    post.getView(),
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
    public void updatePost(PostDto.UpdateRequest updatePostRequest, String username) {
        Post post = postRepository.findById(updatePostRequest.getPostId()).orElseThrow(()->
                new AppException(HttpStatus.NOT_FOUND)
        );
        post.setTitle(updatePostRequest.getTitle());
        post.setOptionA(updatePostRequest.getOptionA());
        post.setOptionB(updatePostRequest.getOptionB());

        postRepository.save(post);
    }

    @Override
    public void deletePost(PostDto.DeleteRequest deletePostRequest, String username) {
        Post post = postRepository.findById(deletePostRequest.getPostId())
                .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND));

        postRepository.delete(post);
    }

    @Override
    public Long toggleToLikePost(PostDto.LikePostRequest likePostRequest, String username) {
        Member member = memberRepository.findByUsername(username)
                .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND));

        Post post = postRepository.findById(likePostRequest.getPostId())
                .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND));

        // 해당 member가 이미 해당 post에 좋아요를 눌렀는지 확인
        Optional<LikePost> optionalLikePost = likePostRepository.findByMember_IdAndPostId(member.getId(), post.getId());

        if (optionalLikePost.isPresent()) {
            // 좋아요가 이미 있다면 삭제
            likePostRepository.delete(optionalLikePost.get());
            log.info("좋아요 삭제");
            return likePostRepository.countByPostId(post.getId());
        } else {
            // 좋아요가 없다면 저장
            LikePost likePost1 = new LikePost(post, member);
            likePostRepository.save(likePost1);
            post.getLikePost().add(likePost1);
            postRepository.save(post);
            log.info("좋아요 저장");
            return likePostRepository.countByPostId(post.getId());
        }
    }


    @Override
    public PostDto.logicResponse barchart() {
        Post randomPost = postRepository.getRamdomPost();

        Long getCommentCountByOptionA = commentRepository.countByPostIdAndSelectOption(randomPost.getId(), "A");
        Long getCommentCountByOptionB = commentRepository.countByPostIdAndSelectOption(randomPost.getId(), "B");

        PostDto.logicResponse logicResponse = new PostDto.logicResponse();
        logicResponse.setPostId(randomPost.getId());
        logicResponse.setTitle(randomPost.getTitle());
        logicResponse.setOptionA(randomPost.getOptionA());
        logicResponse.setOptionB(randomPost.getOptionB());
        logicResponse.setCountByOptionA(getCommentCountByOptionA);
        logicResponse.setCountByOptionB(getCommentCountByOptionB);

        return logicResponse;
    }

    @Override
    public List<PostDto.ListResponse> getPostListByKeyword(String keyword) {
        List<Post> postList = postRepository.getPostByKeyword(keyword);

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