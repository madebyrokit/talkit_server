package com.talkit.service;

import com.talkit.dto.MemberDto;
import com.talkit.entity.Member;
import com.talkit.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{
    private final MemberRepository memberRepository;

    @Override
    public void UploadProfileImage(String email) {

    }

    @Override
    public MemberDto.Response getMemberInfo(String username) {
        Member member = memberRepository.findByUsername(username).orElseThrow();

        MemberDto.Response response = new MemberDto.Response();
        response.setId(member.getId());
        response.setEmail(member.getEmail());
        response.setUsername(member.getUsername());
        response.setMbtiType(member.getMbtitype());
        response.setProfileImage(member.getProfile_image().getStoreFileName());
        return response;
    }

    @Override
    public void DeleteMember(String username) {
        Member member = memberRepository.findByUsername(username).orElseThrow();

        memberRepository.delete(member);
    }
}
