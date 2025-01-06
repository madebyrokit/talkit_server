package com.talkit.service;

import com.talkit.configuration.exception.AppException;
import com.talkit.configuration.jwt.JwtProvider;
import com.talkit.entity.ProfileImage;
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
public class SignServiceImpl implements SignService{

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final MemberRepository memberRepository;
    private final JwtProvider jwtProvider;

    @Override
    public String getSign(SignDto.LoginRequest loginRequest) {
        Member member = memberRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new AppException("아이디 또는 비밀번호가 틀립니다.", HttpStatus.NOT_FOUND));

        if (!bCryptPasswordEncoder.matches(loginRequest.getPassword(), member.getPassword())) {
            log.info("{} password error", member.getEmail());
            throw new AppException("아이디 또는 비밀번호가 틀립니다.", HttpStatus.NOT_FOUND);
        }
        return jwtProvider.createToken(member.getEmail());
    }

    @Override
    public void signup(SignDto.SignUpRequest signUpRequest) {

        Optional<Member> findMember = memberRepository.findByEmail(signUpRequest.getEmail());
        if (!findMember.isEmpty()) {
            throw new AppException("이미 존재하는 회원입니다.", HttpStatus.NOT_FOUND);
        }

        if (!signUpRequest.getPassword().isEmpty() && signUpRequest.getPassword().equals(signUpRequest.getConfirmPassword())) {
            Member member = new Member();
            member.setPassword(bCryptPasswordEncoder.encode(signUpRequest.getPassword()));
            member.setEmail(signUpRequest.getEmail());
            member.setMbtitype(signUpRequest.getMbtiType());
            member.setUsername(signUpRequest.getUsername());
            member.setProfile_image(new ProfileImage("user.png", member));

            memberRepository.save(member);
            log.info("{} registered ", member.getEmail());
        } else if (!signUpRequest.getPassword().equals(signUpRequest.getConfirmPassword())) {
            throw new AppException("비밀번호가 일치하지 않습니다.", HttpStatus.NOT_FOUND);
        }
    }
}
