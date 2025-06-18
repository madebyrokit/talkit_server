package com.talkit.entity;

import com.talkit.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(MockitoExtension.class)
class MemberTest {

    @Mock
    MemberRepository memberRepository;

    @Test
    @DisplayName("맴버 생성 테스트")
    void createMember() {
        //given
        Member member1 = new Member(
                "test1@gmail.com",
                "1234",
                "testUser1",
                "MBTI",
                "OAuth");

        Member member2 = new Member(
                "test2@gmail.com",
                "1234",
                "testUser2",
                "MBTI",
                "OAuth");

        //when, then
        assertThat(member1.getUsername()).isEqualTo("testUser1");
        assertThat(member2.getUsername()).isEqualTo("testUser2");
    }
}