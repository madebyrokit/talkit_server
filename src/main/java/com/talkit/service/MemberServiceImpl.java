package com.talkit.service;

import com.talkit.configuration.exception.AppException;
import com.talkit.dto.MemberDto;
import com.talkit.entity.*;
import com.talkit.repository.*;
import com.talkit.repository.Comments.CommentRepository;
import com.talkit.repository.posts.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberServiceImpl implements MemberService{
    private final MemberRepository memberRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final LikePostRepository likePostRepository;
    private final LikeCommentRepository likeCommentRepository;
    private final FileService fileService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public MemberDto.Response getMember(String email) {

        Member member = memberRepository.findByEmail(email).orElseThrow(
                () -> new AppException("member didn't found", HttpStatus.NOT_FOUND));

        return new MemberDto.Response(
                member.getId(),
                member.getEmail(),
                member.getUsername(),
                member.getAvatar().getStoreFileName(),
                member.getMbtitype(),
                member.getOAuth()
        );
    }

    @Override
    public void deleteMember(String email) {
        Member member = memberRepository.findByEmail(email).orElseThrow(
                ()-> new AppException("맴버를 찾을 수 없습니다.", HttpStatus.NOT_FOUND));

        memberRepository.delete(member);
    }

    @Override
    public MemberDto.Response updateMember(MemberDto.Register request, String email) {
        Member member = memberRepository.findByEmail(email).orElseThrow(
                ()-> new AppException("맴버를 찾을 수 없습니다.", HttpStatus.NOT_FOUND)
        );

        Optional<Member> findUsername = memberRepository.findByUsername(request.getUsername());
        if (findUsername.isEmpty() || findUsername.get().getUsername().equals(member.getUsername())) {
            member.setUsername(request.getUsername());
        } else {
            throw new AppException("사용자 이름이 이미 존재합니다.", HttpStatus.CONFLICT);
        }

        if (!request.getPassword().isEmpty() && request.getPassword().equals(request.getConfirmPassword())) {
            member.setPassword(bCryptPasswordEncoder.encode(request.getPassword()));
        } else if (!request.getPassword().equals(request.getConfirmPassword())) {
            throw new AppException("비밀번호가 일치하지 않습니다.", HttpStatus.CONFLICT);
        }

        member.setMbtitype(request.getMbtiType());

        memberRepository.save(member);


        return new MemberDto.Response(
                member.getId(),
                member.getEmail(),
                member.getUsername(),
                member.getAvatar().getStoreFileName(),
                member.getMbtitype(),
                member.getOAuth()
        );
    }

    @Override
    public List<MemberDto.ResponsePostList> getPostListByMember(String email) {
        Member member = memberRepository.findByEmail(email).orElseThrow(
                () -> new AppException("맴버를 찾을 수 없습니다.", HttpStatus.NOT_FOUND)
        );

        List<Post> postList = postRepository.findByMemberId(member.getId());
        List<MemberDto.ResponsePostList> responsePostList = new ArrayList<>();

        for (Post post : postList) {
            MemberDto.ResponsePostList responsePost = new MemberDto.ResponsePostList();
            responsePost.setPostId(post.getId());
            responsePost.setTitle(post.getTitle());
            responsePost.setOpinion_a(post.getOption_a());
            responsePost.setOpinion_b(post.getOption_b());
            responsePost.setCreated_at(post.getCreated_at());
            responsePostList.add(responsePost);
        }
        return responsePostList;
    }

    @Override
    public List<MemberDto.ResponseCommentList> getCommentListByMember(String email) {
        Member member = memberRepository.findByEmail(email).orElseThrow(
                () -> new AppException("맴버를 찾을 수 없습니다.", HttpStatus.NOT_FOUND));

        List<Comment> commentList = commentRepository.findByMemberId(member.getId());
        List<MemberDto.ResponseCommentList> responseCommentList = new ArrayList<>();

        for (Comment comment : commentList) {
            MemberDto.ResponseCommentList responseComment = new MemberDto.ResponseCommentList();
            responseComment.setComment_id(comment.getId());
            responseComment.setContent(comment.getContent());
            responseComment.setOpinion(comment.getOpinion());
            responseComment.setCreated_at(comment.getCreated_at());
            responseCommentList.add(responseComment);
        }
        return responseCommentList;
    }

    @Override
    public List<MemberDto.ResponseLikedPostList> getLikedPostListByMember(String email) {
        Member member = memberRepository.findByEmail(email).orElseThrow(
                () -> new AppException("맴버를 찾을 수 없습니다.", HttpStatus.NOT_FOUND));

        List<LikePost> likePostList = likePostRepository.findByMemberId(member.getId());
        List<MemberDto.ResponseLikedPostList> responseLikedPostList = new ArrayList<>();

        for (LikePost likePost : likePostList) {
            MemberDto.ResponseLikedPostList responseLikedPost = new MemberDto.ResponseLikedPostList();
            responseLikedPost.setPost_id(likePost.getPost().getId());
            responseLikedPost.setTitle(likePost.getPost().getTitle());
            responseLikedPost.setCreated_at(likePost.getPost().getCreated_at());
            responseLikedPostList.add(responseLikedPost);
        }
        return responseLikedPostList;
    }

    @Override
    public List<MemberDto.ResponseLikedCommentList> getLikedCommentListByMember(String email) {
        Member member = memberRepository.findByEmail(email).orElseThrow(
                () -> new AppException("맴버를 찾을 수 없습니다.", HttpStatus.NOT_FOUND));

        List<LikeComment> likeCommentList = likeCommentRepository.findByMemberId(member.getId());
        List<MemberDto.ResponseLikedCommentList> responseLikedCommentList = new ArrayList<>();

        for (LikeComment likeComment : likeCommentList) {
            MemberDto.ResponseLikedCommentList responseLikedComment = new MemberDto.ResponseLikedCommentList();
            responseLikedComment.setComment_id(likeComment.getComment().getId());
            responseLikedComment.setContent(likeComment.getComment().getContent());
            responseLikedComment.setCreated_at(likeComment.getComment().getCreated_at());
            responseLikedCommentList.add(responseLikedComment);
        }
        return responseLikedCommentList;
    }
}
