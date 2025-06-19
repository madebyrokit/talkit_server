package com.talkit.repository;

import com.talkit.QueryDslConfig;
import com.talkit.entity.Member;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@Import(QueryDslConfig.class)
@ActiveProfiles("test")
@DataJpaTest
class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @BeforeEach
    void setMemberRepository() {
        memberRepository.deleteAll();
    }

    @Test
    @DisplayName("맴버 저장")
    void save() {
        // given
        Member member = new Member("test1@gmail.com", "1234", "testUser1", "MBTI", "OAuth");
        memberRepository.save(member);

        // when
        Member savedMember = memberRepository.save(member);

        // then
        assertThat(savedMember.getUsername()).isEqualTo("testUser1");
    }

    @Test
    @DisplayName("맴버 아이디 조회")
    void findById() {
        //given
        Member member = new Member("test1@gmail.com", "1234", "testUser1", "MBTI", "");
        Member savedMember = memberRepository.save(member);

        //when
        Optional<Member> foundMember = memberRepository.findById(savedMember.getId());

        //then
        assertThat(foundMember.get().getUsername()).isEqualTo("testUser1");
    }

    @Test
    @DisplayName("맴버 삭제")
    void delete() {
        //given
        Member member1 = new Member("test1@gmail.com", "1234", "testUser1", "MBTI", "OAuth");
        memberRepository.save(member1);
        //when
        memberRepository.delete(member1);
        // then
        Optional<Member> deleted = memberRepository.findById(member1.getId());
        assertThat(deleted).isNotPresent();
    }

    @Test
    @DisplayName("모든 맴버 조회")
    void findAllMembers() {
        //given
        Member member1 = new Member("test1@gmail.com", "1234", "testUser1", "MBTI", "OAuth");
        Member member2 = new Member("test2@gmail.com", "1234", "testUser2", "MBTI", "OAuth");
        Member member3 = new Member("test3@gmail.com", "1234", "testUser3", "MBTI", "OAuth");
        memberRepository.save(member1);
        memberRepository.save(member2);
        memberRepository.save(member3);
        //when
        List<Member> memberList = memberRepository.findAll();
        // then
        assertThat(memberList).hasSize(3);
        assertThat(memberList.get(0).getUsername()).isEqualTo("testUser1");
        assertThat(memberList.get(1).getUsername()).isEqualTo("testUser2");
        assertThat(memberList.get(2).getUsername()).isEqualTo("testUser3");
    }
}