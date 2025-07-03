package com.talkit.service;

import com.talkit.configuration.exception.AppException;
import com.talkit.configuration.jwt.JwtProvider;

import com.talkit.entity.Avatar;
import com.talkit.entity.Member;
import com.talkit.dto.SignDto;
import com.talkit.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class SignServiceImpl implements SignService {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final MemberRepository memberRepository;
    private final JwtProvider jwtProvider;

    @Override
    public String login(SignDto.LoginRequest loginRequest) {
        Member member = memberRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new AppException("아이디 또는 비밀번호가 틀립니다.", HttpStatus.UNAUTHORIZED));

        if (!bCryptPasswordEncoder.matches(loginRequest.getPassword(), member.getPassword())) {
            log.info("{} password error", member.getEmail());
            throw new AppException("아이디 또는 비밀번호가 틀립니다.", HttpStatus.UNAUTHORIZED);
        }
        return jwtProvider.createToken(member.getEmail());
    }

    @Override
    public String register(SignDto.SignUpRequest signUpRequest) {
        validateRegistration(signUpRequest);

        Member member = Member.builder()
                .email(signUpRequest.getEmail())
                .password(bCryptPasswordEncoder.encode(signUpRequest.getPassword()))
                .username(signUpRequest.getUsername())
                .mbtitype(signUpRequest.getMbti_type())
                .oAuth("default")
                .build();

        member.setAvatar(new Avatar("default.jpg", member));
        memberRepository.save(member);

        log.info("{} is registered", member.getEmail());
        return member.getEmail();
    }

    private void validateRegistration(SignDto.SignUpRequest signUpRequest) {
        if (memberRepository.findByEmail(signUpRequest.getEmail()).isPresent()) {
            throw new AppException("이미 사용 중인 이메일입니다.", HttpStatus.CONFLICT);
        }
        if (!signUpRequest.getPassword().equals(signUpRequest.getConfirm_password())) {
            throw new AppException("비밀번호가 일치하지 않습니다.", HttpStatus.BAD_REQUEST);
        }
    }
}
