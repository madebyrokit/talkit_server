package com.talkit.service;

import com.talkit.configuration.jwt.JwtProvider;
import com.talkit.dto.SignDto;
import com.talkit.entity.Member;
import com.talkit.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;


import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;


@ExtendWith(MockitoExtension.class)
class SignServiceImplTest {

    @Mock
    private MemberRepository memberRepository;
    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Mock
    private JwtProvider jwtProvider;
    @InjectMocks
    private SignServiceImpl signService;


    @Test
    @DisplayName("회원가입 성공 시 이메일 반환")
    void register() {
        //given
        SignDto.SignUpRequest signUpRequest = new SignDto.SignUpRequest(
                "test@gmail.com",
                "1234",
                "1234",
                "testUser",
                "MBTI");

        //when
        String savedEmail = signService.register(signUpRequest);

        //then
        Assertions.assertThat(savedEmail).isEqualTo("test@gmail.com");
    }

    @Test
    @DisplayName("로그인 성공 시 토큰 반환")
    void login() {
        // given
        SignDto.LoginRequest loginRequest = new SignDto.LoginRequest("test@gmail.com", "1234");

        Member mockMember = new Member("test@gmail.com", "encodedPassword", "testUser", "MBTI", "");

        Mockito.when(memberRepository.findByEmail("test@gmail.com")).thenReturn(Optional.of(mockMember));
        Mockito.when(bCryptPasswordEncoder.matches("1234", "encodedPassword")).thenReturn(true);
        Mockito.when(jwtProvider.createToken(Mockito.anyString())).thenReturn("mocked-jwt-token");

        // when
        String token = signService.login(loginRequest);

        // then
        Assertions.assertThat(token).isEqualTo("mocked-jwt-token");
    }
}