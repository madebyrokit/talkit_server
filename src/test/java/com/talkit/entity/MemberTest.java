package com.talkit.entity;

import com.talkit.QueryDslConfig;
import com.talkit.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;

import static org.springframework.test.util.AssertionErrors.assertEquals;

@Import(QueryDslConfig.class)
@DataJpaTest
class MemberTest {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    MemberRepository memberRepository;

    @Test
    @DisplayName("맴버 생성 테스트")
    void createMember() {
        //given
        Member member1 = new Member(
                "test1@test.com",
                "1234",
                "testUser1",
                "MBTI",
                "OAuth");

        testEntityManager.persist(member1);

        //when
        Member found1 = memberRepository.findById(member1.getId()).orElseThrow();

        //then
        assertEquals("", found1.getEmail(), "test1@test.com");
    }
}