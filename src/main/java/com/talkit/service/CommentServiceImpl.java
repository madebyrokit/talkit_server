package com.talkit.service;

import com.talkit.dto.CommentDto;
import com.talkit.configuration.exception.AppException;
import com.talkit.entity.Comment;
import com.talkit.entity.LikeComment;
import com.talkit.entity.Member;
import com.talkit.entity.Post;
import com.talkit.repository.CommentRepository;
import com.talkit.repository.LikeCommentRepository;
import com.talkit.repository.MemberRepository;
import com.talkit.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final MemberRepository memberRepository;
    private final CommentRepository commentRepository;
    private final LikeCommentRepository likeCommentRepository;
    private final PostRepository postRepository;

    @Override
    public CommentDto.CreateResponse createComment(CommentDto.CreateRequest createRequest, String username) {
        Member member = memberRepository.findByUsername(username)
                .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND));

        Post post = postRepository.findById(createRequest.getPostId())
                .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND));

        Comment comment = new Comment(
                createRequest.getContent(),
                post,
                member,
                createRequest.getOption(),
                new Date()
        );

        commentRepository.save(comment);

        return new CommentDto.CreateResponse(
                comment.getPost().getId(),
                comment.getId(),
                comment.getMember().getUsername(),
                comment.getContent(),
                comment.getMember().getMbtitype(),
                comment.getMember().getProfile_image().getStoreFileName(),
                comment.getSelectOption(),
                likeCommentRepository.countByCommentId(comment.getId()),
                comment.getCreatedAt()
        );
    }

    @Override
    public List<CommentDto.GetResponse> getCommentList(int page, int size, Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND));

        PageRequest pageRequest = PageRequest.of(page, size);
        List<Comment> commentList = commentRepository.getCommentList(pageRequest, post.getId());


        List<CommentDto.GetResponse> getResponseList = new ArrayList<>();

        if (commentList != null ) {
            for (Comment comment : commentList) {
                CommentDto.GetResponse getResponse = new CommentDto.GetResponse();
                getResponse.setPostId(comment.getPost().getId());
                getResponse.setCommentId(comment.getId());
                getResponse.setUsername(comment.getMember().getUsername());
                getResponse.setProfileImage(comment.getMember().getProfile_image().getStoreFileName());
                getResponse.setMbtiType(comment.getMember().getMbtitype());
                getResponse.setContent(comment.getContent());
                getResponse.setOption(comment.getSelectOption());
                getResponse.setLike(likeCommentRepository.countByCommentId(comment.getId()));
                getResponse.setCreateAt(comment.getCreatedAt());

                getResponseList.add(getResponse);
            }
            return getResponseList;
        } else {
            return null;
        }
    }

    @Override
    public Boolean updateComment(CommentDto.UpdateRequest updateCommentRequest, String username) {
        Comment comment = commentRepository.findById(updateCommentRequest.getPostId())
                .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND));

        comment.setContent(updateCommentRequest.getContent());
        comment.setSelectOption(updateCommentRequest.getOption());


        return true;
    }

    @Override
    public void deleteComment(CommentDto.DeleteRequest deleteCommentRequest, String username) {
        Comment comment = commentRepository.findById(deleteCommentRequest.getCommentId())
                .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND));

        commentRepository.delete(comment);
    }

    @Override
    public Long toggleLike(Long commentId, String username) {
        Member member = memberRepository.findByUsername(username)
                .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND));

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND));

        // 해당 member가 이미 해당 post에 좋아요를 눌렀는지 확인
        Optional<LikeComment> optionalLikeComment = likeCommentRepository.findByMember_IdAndCommentId(member.getId(), comment.getId());

        if (optionalLikeComment.isPresent()) {
            // 좋아요가 이미 있다면 삭제
            likeCommentRepository.delete(optionalLikeComment.get());
            log.info("댓글 좋아요 삭제");
            return likeCommentRepository.countByCommentId(comment.getId());
        } else {
            // 좋아요가 없다면 저장
            LikeComment likeComment = new LikeComment(comment, member);
            likeCommentRepository.save(likeComment);
            comment.getLikeComment().add(likeComment);
            commentRepository.save(comment);
            log.info("댓글 좋아요 저장");
            return likeCommentRepository.countByCommentId(comment.getId());
        }

    }
}