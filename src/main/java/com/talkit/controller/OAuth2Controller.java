package com.talkit.controller;

import com.talkit.service.OAuth2Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class OAuth2Controller {

    private final OAuth2Service oAuth2Service;

    @GetMapping("/kakao")
    public ResponseEntity<String> kakao(@RequestParam String code) {
        String token = oAuth2Service.kakao(code);
        return ResponseEntity.ok("kakao");
    }

    @GetMapping("/naver")
    public ResponseEntity<?> naver(@RequestParam String code) {
        String token = oAuth2Service.naver(code);
        return ResponseEntity.ok("naver");
    }
}
