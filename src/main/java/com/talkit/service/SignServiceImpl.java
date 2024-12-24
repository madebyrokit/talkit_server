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
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class SignServiceImpl implements SignService{

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final MemberRepository memberRepository;
    private final JwtProvider jwtProvider;
    private final FileService fileService;


    @Override
    public void signup(SignDto.SignUpRequest signUpRequest) {

        Member member = new Member();
        member.setEmail(signUpRequest.getEmail());
        member.setPassword(bCryptPasswordEncoder.encode(signUpRequest.getPassword()));
        member.setMbtitype(signUpRequest.getMbtiType());
        member.setUsername(signUpRequest.getUsername());

        member.setProfile_image(new ProfileImage("user.png", member));

        memberRepository.save(member);
        log.info("{} registered ", member.getEmail());
    }

    @Override
    public String validatateFromSign(SignDto.LoginRequest loginRequest) {
        Member member = memberRepository.findAllByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND));

        bCryptPasswordEncoder.matches(member.getPassword(), loginRequest.getPassword());


        return jwtProvider.createToken(member.getUsername());

    }


}
