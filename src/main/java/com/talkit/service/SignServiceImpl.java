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
                .orElseThrow(() -> new AppException("아이디 또는 비밀번호가 틀립니다.", HttpStatus.NOT_FOUND));

        if (!bCryptPasswordEncoder.matches(loginRequest.getPassword(), member.getPassword())) {
            log.info("{} password error", member.getEmail());
            throw new AppException("아이디 또는 비밀번호가 틀립니다.", HttpStatus.NOT_FOUND);
        }
        return jwtProvider.createToken(member.getEmail());
    }

    @Override
    public String register(SignDto.SignUpRequest signUpRequest) {

        Optional<Member> optionalMember = memberRepository.findByEmail(signUpRequest.getEmail());

        if (!optionalMember.isEmpty()) {
            throw new AppException("This member already exists", HttpStatus.NOT_FOUND);
        } else if (!signUpRequest.getConfirm_password().matches(signUpRequest.getPassword())) {
            throw new AppException("This passwords do not match", HttpStatus.NOT_FOUND);
        }

        Member member = new Member();
        member.setPassword(bCryptPasswordEncoder.encode(signUpRequest.getPassword()));
        member.setEmail(signUpRequest.getEmail());
        member.setMbtitype(signUpRequest.getMbti_type());
        member.setUsername(signUpRequest.getUsername());
        member.setAvatar(new Avatar("user.png", member));
        member.setOAuth("");
        memberRepository.save(member);
        log.info("{} is registered ", member.getEmail());
        return member.getEmail();
    }
}
