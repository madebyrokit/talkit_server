package backend.mbti.controller;

import backend.mbti.service.KakaoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
@Slf4j
public class OAuthController {

    private final KakaoService kakaoService;

    @GetMapping("/kakao")
    public ResponseEntity<String> kakaoCallback(@RequestParam String code) {
        String token = kakaoService.processKakaoCallback(code);
        return ResponseEntity.ok(token);
    }
}
