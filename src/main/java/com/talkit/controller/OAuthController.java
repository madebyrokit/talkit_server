package com.talkit.controller;

import com.talkit.service.OAuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class OAuthController {

    private final OAuthService OAuthService;

    @GetMapping("/kakao")
    public ResponseEntity<String> kakaoCallback(@RequestParam String code) {
        String token = OAuthService.processKakaoCallback(code);
        return ResponseEntity.ok(token);
    }

    @GetMapping("/naver")
    public ResponseEntity<?> naverCallBack(@RequestParam String code) {
        return ResponseEntity.ok().build();
    }
}
