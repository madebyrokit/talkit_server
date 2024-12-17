package backend.mbti.service;

import backend.mbti.entity.*;
import backend.mbti.dto.CommentDto;
import backend.mbti.dto.PostDto;

import backend.mbti.configuration.exception.AppException;
import backend.mbti.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.*;

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
                newPost.getMember().getProfileImage().getStoreFileName(),
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
    public Page<PostDto.ListResponse> getListByPost(int page, int size) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Order.desc("id")));
        Page<Post> pagePosts = postRepository.findAll(pageable);

        return pagePosts.map(post -> new PostDto.ListResponse(
                        post.getId(),
                        post.getMember().getId(),
                        post.getMember().getUsername(),
                        post.getMember().getMbtitype(),
                        post.getMember().getProfileImage().getStoreFileName(),
                        post.getTitle(),
                        post.getOptionA(),
                        post.getOptionB(),
                        commentRepository.countByPostId(post.getId()),
                        post.getCreatedAt(),
                        post.getView(),
                        likePostRepository.countByPostId(post.getId())
                        ));
    }

    @Override
    public PostDto.Response getPost(Long postId) {
        Optional<Post> optionalPost = postRepository.findById(postId);

        if (optionalPost.isPresent()) {
            Post post = optionalPost.get();
            post.incrementView();
            postRepository.save(post);

            List<Comment> comments = commentRepository.findAllByPostIdOrderByIdDesc(postId);
            List<CommentDto.GetResponse> getResponse = new ArrayList<>();

            for (Comment comment : comments) {
                getResponse.add(new CommentDto.GetResponse(
                        post.getId(),
                        comment.getId(),
                        comment.getMember().getUsername(),
                        comment.getContent(),
                        comment.getMember().getMbtitype(),
                        comment.getMember().getProfileImage().getStoreFileName(),
                        comment.getSelectOption(),
                        likeCommentRepository.countByCommentId(comment.getId()),
                        post.getCreatedAt(),
                        comment.getCreatedAt()));
            }

            return new PostDto.Response(
                    post.getId(),
                    post.getMember().getId(),
                    post.getMember().getUsername(),
                    post.getMember().getMbtitype(),
                    post.getMember().getProfileImage().getStoreFileName(),
                    post.getTitle(),
                    post.getOptionA(),
                    post.getOptionB(),
                    post.getCreatedAt(),
                    post.getView(),
                    likePostRepository.countByPostId(post.getId()),
                    commentRepository.countByPostId(post.getId()),
                    commentRepository.countByPostIdAndSelectOption(post.getId(), "A"),
                    commentRepository.countByPostIdAndSelectOption(post.getId(), "B"),
                    getResponse);
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
        Optional<LikePost> optionalLikePost = likePostRepository.findByMemberIdAndPostId(member.getId(), post.getId());

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
        Long randomPostId = postRepository.findRandomPost().getId();
        Optional<Post> post = postRepository.findById(randomPostId);

        Long getCommentCountByOptionA = commentRepository.countByPostIdAndSelectOption(post.get().getId(), "A");
        Long getCommentCountByOptionB = commentRepository.countByPostIdAndSelectOption(post.get().getId(), "B");

        PostDto.logicResponse logicResponse = new PostDto.logicResponse();
        logicResponse.setPostId(post.get().getId());
        logicResponse.setTitle(post.get().getTitle());
        logicResponse.setOptionA(post.get().getOptionA());
        logicResponse.setOptionB(post.get().getOptionB());
        logicResponse.setCountByOptionA(getCommentCountByOptionA);
        logicResponse.setCountByOptionB(getCommentCountByOptionB);

        return logicResponse;
    }
}