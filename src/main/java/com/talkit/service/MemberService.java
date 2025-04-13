package com.talkit.service;

import com.talkit.dto.MemberDto;

import java.util.List;

public interface  MemberService {
    MemberDto.Response getMember(String email);
    void deleteMember(String email);
    MemberDto.Response updateMember(MemberDto.Register request, String email);
    List<MemberDto.ResponsePostList> getPostListByMember(String email);
    List<MemberDto.ResponseCommentList> getCommentListByMember(String email);
    List<MemberDto.ResponseLikedPostList> getLikedPostListByMember(String email);
    List<MemberDto.ResponseLikedCommentList> getLikedCommentListByMember(String email);

}
