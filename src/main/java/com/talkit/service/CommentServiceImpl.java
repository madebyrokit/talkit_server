package com.talkit.service;

import com.talkit.dto.CommentDto;
import com.talkit.configuration.exception.AppException;
import com.talkit.entity.Comment;
import com.talkit.entity.LikeComment;
import com.talkit.entity.Member;
import com.talkit.entity.Post;
import com.talkit.repository.Comments.CommentRepository;
import com.talkit.repository.LikeCommentRepository;
import com.talkit.repository.MemberRepository;
import com.talkit.repository.posts.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
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
    public CommentDto.CreateResponse createComment(CommentDto.CreateRequest createRequest, String userEmail) {
        Member member = memberRepository.findByEmail(userEmail)
                .orElseThrow(() -> new AppException("맴버를 찾을 수 없습니다.", HttpStatus.NOT_FOUND));

        Post post = postRepository.findById(createRequest.getPostId())
                .orElseThrow(() -> new AppException("게시글을 찾을 수 없습니다.", HttpStatus.NOT_FOUND));

        Comment comment = new Comment(
                createRequest.getContent(),
                post,
                member,
                createRequest.getOpinion(),
                new Date()
        );

        commentRepository.save(comment);

        return new CommentDto.CreateResponse(
                comment.getPost().getId(),
                comment.getId(),
                comment.getMember().getUsername(),
                comment.getContent(),
                comment.getMember().getMbtitype(),
                comment.getMember().getAvatar().getStoreFileName(),
                comment.getSelectOption(),
                likeCommentRepository.countByCommentId(comment.getId()),
                comment.getCreatedAt()
        );
    }

    @Override
    public List<CommentDto.GetResponse> getCommentList(int page, int size, Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new AppException("게시글을 찾을 수 없습니다", HttpStatus.NOT_FOUND));

        PageRequest pageRequest = PageRequest.of(page, size);
        List<Comment> commentList = commentRepository.getCommentList(pageRequest, post.getId());


        List<CommentDto.GetResponse> getResponseList = new ArrayList<>();

        if (commentList != null ) {
            for (Comment comment : commentList) {
                CommentDto.GetResponse getResponse = new CommentDto.GetResponse();
                getResponse.setPostId(comment.getPost().getId());
                getResponse.setCommentId(comment.getId());
                getResponse.setUsername(comment.getMember().getUsername());
                getResponse.setProfileImage(comment.getMember().getAvatar().getStoreFileName());
                getResponse.setMbtiType(comment.getMember().getMbtitype());
                getResponse.setContent(comment.getContent());
                getResponse.setOption(comment.getSelectOption());
                getResponse.setLike(likeCommentRepository.countByCommentId(comment.getId()));
                getResponse.setCreatedAt(comment.getCreatedAt());

                getResponseList.add(getResponse);
            }
            return getResponseList;
        } else {
            return null;
        }
    }

    @Override
    public CommentDto.UpdateResponse updateComment(CommentDto.UpdateRequest updateCommentRequest, String username) {
        Comment comment = commentRepository.findById(updateCommentRequest.getCommentId())
                .orElseThrow(() -> new AppException("댓글을 찾을 수 없습니다", HttpStatus.NOT_FOUND));

        comment.setContent(updateCommentRequest.getContent());
        comment.setSelectOption(updateCommentRequest.getSelectedOpinion());

        commentRepository.save(comment);
        return new CommentDto.UpdateResponse(comment.getId(), comment.getContent(), comment.getSelectOption());
    }

    @Override
    public void deleteComment(Long commentId, String username) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new AppException("댓글을 찾을 수 없습니다", HttpStatus.NOT_FOUND));

        commentRepository.delete(comment);
    }

    @Override
    public Long toggleLike(Long commentId, String userEmail) {
        Member member = memberRepository.findByEmail(userEmail)
                .orElseThrow(() -> new AppException("맴버를 찾을 수 없습니다", HttpStatus.NOT_FOUND));

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new AppException("댓글을 찾을 수 없습니다", HttpStatus.NOT_FOUND));

        Optional<LikeComment> optionalLikeComment = likeCommentRepository.findByMember_IdAndCommentId(member.getId(), comment.getId());

        if (optionalLikeComment.isPresent()) {
            likeCommentRepository.delete(optionalLikeComment.get());
            log.info("댓글 좋아요 삭제");
            return likeCommentRepository.countByCommentId(comment.getId());
        } else {
            LikeComment likeComment = new LikeComment(comment, member);
            likeCommentRepository.save(likeComment);
            comment.getLikeComment().add(likeComment);
            commentRepository.save(comment);
            log.info("댓글 좋아요 저장");
            return likeCommentRepository.countByCommentId(comment.getId());
        }

    }
}