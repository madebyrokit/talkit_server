package backend.mbti.service;

import backend.mbti.configuration.jwt.JwtProvider;
import backend.mbti.entity.ProfileImage;
import backend.mbti.entity.Member;
import backend.mbti.dto.SignDto;
import backend.mbti.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class SignServiceImpl implements SignService{

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final MemberRepository memberRepository;

    @Value("${jwt.secret}")
    private String secretKey;
    private Long expireTimeMs = 1000 * 60 * 60L;

    @Override
    public void signup(SignDto.SignUpRequest signUpRequest) {
        String encPwd = bCryptPasswordEncoder.encode(signUpRequest.getPassword());

        Member member = new Member(
                signUpRequest.getEmail(),
                encPwd,
                signUpRequest.getUsername(),
                signUpRequest.getMbtitype(),
                null
        );
        member.setProfileImage(new ProfileImage("default.jpg", member));

        memberRepository.save(member);
        log.info("{} registered ", member.getEmail());
    }

    @Override
    public String validToSign(SignDto.LoginRequest loginRequest) {
        Optional<Member> member = memberRepository.findAllByEmail(loginRequest.getEmail());

        if (!member.isPresent()) {
            return "Invalid";
        }
        if (!(bCryptPasswordEncoder.matches(loginRequest.getPassword(), member.get().getPassword()))) {
            return "Invalid";
        }

        return JwtProvider.createToken(member.get().getUsername(), secretKey, expireTimeMs);
    }


}
