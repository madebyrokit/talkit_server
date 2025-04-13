package com.talkit.service;

import com.talkit.dto.MemberDto;

import java.util.List;

public interface  MemberService {
    MemberDto.Response getMember(String email);
    void deleteMember(String userEmail);
    MemberDto.Response updateMember(MemberDto.Register request, String userEmail);
    List<MemberDto.ResponsePostList> getPostListByMember(String userEmail);
    List<MemberDto.ResponseCommentList> getCommentListByMember(String userEmail);
    List<MemberDto.ResponseLikedPostList> getLikedPostListByMember(String userEmail);
    List<MemberDto.ResponseLikedCommentList> getLikedCommentListByMember(String userEmail);

}
