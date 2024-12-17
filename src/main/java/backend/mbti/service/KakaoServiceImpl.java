package backend.mbti.service;

import backend.mbti.dto.KakaoDto;
import backend.mbti.repository.MemberRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
@Slf4j
public class KakaoServiceImpl implements KakaoService {

    private final AuthenticationManager authenticationManager;
    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Value("${jwt.secret}")
    private String key;
    private Long expireTimeMs = 1000 * 60 * 60L;
    @Override
    public String processKakaoCallback(String code) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", "9394c1ee0de2fd55a8ccc154f6cc5114");
        params.add("client_secret", "vDoTob3tFhPi0BPrrZEfTwc01tvcjb8S");
        params.add("redirect_uri", "http://localhost:3000/kakao");
        params.add("code", code);

        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = new HttpEntity<>(params, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                "https://kauth.kakao.com/oauth/token",
                HttpMethod.POST,
                kakaoTokenRequest,
                String.class
        );

        ObjectMapper objectMapper = new ObjectMapper();
        KakaoDto.KakaoJwt kakaoJwt = null;
        try {
            kakaoJwt = objectMapper.readValue(response.getBody(), KakaoDto.KakaoJwt.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        RestTemplate rt2 = new RestTemplate();

        HttpHeaders headers2 = new HttpHeaders();
        headers2.add("Authorization", "Bearer " + kakaoJwt.getAccess_token());
        headers2.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest = new HttpEntity<>(headers2);

        ResponseEntity<String> response2 = rt2.exchange(
                "https://kapi.kakao.com/v2/user/me",
                HttpMethod.POST,
                kakaoProfileRequest,
                String.class
        );
        log.info(response2.toString());

        ObjectMapper objectMapper2 = new ObjectMapper();
        KakaoDto.Properties kakaoProfile = null;

        String responseBody = response2.getBody();

        try {
            kakaoProfile = objectMapper2.readValue(responseBody, KakaoDto.Properties.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        log.info(kakaoProfile.getNickname());

        return new String("임시");
    }
}


