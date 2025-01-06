package com.talkit.service;

import com.talkit.configuration.exception.AppException;
import com.talkit.configuration.jwt.JwtProvider;
import com.talkit.dto.SignDto;
import com.talkit.entity.Member;
import com.talkit.entity.ProfileImage;
import com.talkit.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SignServiceImplTest {

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Mock
    private JwtProvider jwtProvider;

    @InjectMocks
    private SignServiceImpl signService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getSign_ValidCredentials_ShouldReturnToken() {
        // given
        SignDto.LoginRequest loginRequest = new SignDto.LoginRequest();
        loginRequest.setEmail("test@example.com");
        loginRequest.setPassword("password123");

        Member member = new Member();
        member.setEmail("test@example.com");
        member.setPassword("encodedPassword");

        when(memberRepository.findByEmail(loginRequest.getEmail())).thenReturn(Optional.of(member));
        when(bCryptPasswordEncoder.matches(loginRequest.getPassword(), member.getPassword())).thenReturn(true);
        when(jwtProvider.createToken(member.getEmail())).thenReturn("testToken");

        // when
        String token = signService.getSign(loginRequest);

        // then
        assertEquals("testToken", token);
        verify(memberRepository, times(1)).findByEmail(loginRequest.getEmail());
        verify(bCryptPasswordEncoder, times(1)).matches(loginRequest.getPassword(), member.getPassword());
        verify(jwtProvider, times(1)).createToken(member.getEmail());
    }

    @Test
    void getSign_InvalidPassword_ShouldThrowAppException() {
        // given
        SignDto.LoginRequest loginRequest = new SignDto.LoginRequest();
        loginRequest.setEmail("test@example.com");
        loginRequest.setPassword("wrongPassword");

        Member member = new Member();
        member.setEmail("test@example.com");
        member.setPassword("encodedPassword");

        when(memberRepository.findByEmail(loginRequest.getEmail())).thenReturn(Optional.of(member));
        when(bCryptPasswordEncoder.matches(loginRequest.getPassword(), member.getPassword())).thenReturn(false);

        // when & then
        AppException exception = assertThrows(AppException.class, () -> signService.getSign(loginRequest));
        assertEquals("아이디 또는 비밀번호가 틀립니다.", exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getHttpStatus());
        verify(bCryptPasswordEncoder, times(1)).matches(loginRequest.getPassword(), member.getPassword());
    }

    @Test
    void signup_ValidRequest_ShouldSaveMember() {
        // given
        SignDto.SignUpRequest signUpRequest = new SignDto.SignUpRequest();
        signUpRequest.setEmail("newuser@example.com");
        signUpRequest.setPassword("password123");
        signUpRequest.setConfirmPassword("password123");
        signUpRequest.setMbtiType("INTJ");
        signUpRequest.setUsername("New User");

        when(memberRepository.findByEmail(signUpRequest.getEmail())).thenReturn(Optional.empty());
        when(bCryptPasswordEncoder.encode(signUpRequest.getPassword())).thenReturn("encodedPassword");

        // when
        signService.signup(signUpRequest);

        // then
        verify(memberRepository, times(1)).save(any(Member.class));
    }

    @Test
    void signup_EmailAlreadyExists_ShouldThrowAppException() {
        // given
        SignDto.SignUpRequest signUpRequest = new SignDto.SignUpRequest();
        signUpRequest.setEmail("existing@example.com");
        signUpRequest.setPassword("password123");
        signUpRequest.setConfirmPassword("password123");

        Member existingMember = new Member();
        existingMember.setEmail("existing@example.com");

        when(memberRepository.findByEmail(signUpRequest.getEmail())).thenReturn(Optional.of(existingMember));

        // when & then
        AppException exception = assertThrows(AppException.class, () -> signService.signup(signUpRequest));
        assertEquals("이미 존재하는 회원입니다.", exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getHttpStatus());
        verify(memberRepository, never()).save(any(Member.class));
    }

    @Test
    void signup_PasswordsDoNotMatch_ShouldThrowAppException() {
        // given
        SignDto.SignUpRequest signUpRequest = new SignDto.SignUpRequest();
        signUpRequest.setEmail("newuser@example.com");
        signUpRequest.setPassword("password123");
        signUpRequest.setConfirmPassword("wrongPassword");

        // when & then
        AppException exception = assertThrows(AppException.class, () -> signService.signup(signUpRequest));
        assertEquals("비밀번호가 일치하지 않습니다.", exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getHttpStatus());
        verify(memberRepository, never()).save(any(Member.class));
    }
}
