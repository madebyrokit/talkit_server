package com.talkit.service;

import com.talkit.dto.MemberDto;
import com.talkit.entity.Member;
import com.talkit.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final MemberRepository memberRepository;

    public List<MemberDto.Response> getListMember() {
        List<Member> ListMember = memberRepository.findAll();

        List<MemberDto.Response> responseList = new ArrayList<>();

        for (Member member : ListMember) {
            MemberDto.Response memberDto = new MemberDto.Response(
                    member.getId(),
                    member.getEmail(),
                    member.getUsername(),
                    member.getAvatar().getStoreFileName(),
                    member.getMbtitype(),
                    member.getOAuth()
                    );
            responseList.add(memberDto);
        }

        return responseList;
    }
}
