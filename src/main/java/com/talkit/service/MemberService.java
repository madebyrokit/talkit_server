package com.talkit.service;

import com.talkit.dto.MemberDto;

public interface  MemberService {
    void UploadProfileImage(String email);
    MemberDto.Response getMemberInfo(String username);
    void DeleteMember(String username);
}
