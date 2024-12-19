package com.talkit.controller;

import com.talkit.configuration.jwt.JwtProvider;
import com.talkit.dto.SignDto;
import com.talkit.service.SignService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RequiredArgsConstructor
@RestController
public class SignController {
    private final SignService signService;
    private final JwtProvider jwtProvider;

    @PostMapping("/signin")
    public ResponseEntity<String> login(@RequestBody SignDto.LoginRequest loginRequest) {
        String token = signService.validatateFromSign(loginRequest);
        HttpHeaders headers = new HttpHeaders();
        headers.add("authorization", "Bearer " + token);
        return ResponseEntity.ok().headers(headers).body(token);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> sign(@RequestBody SignDto.SignUpRequest signUpRequest) {
        signService.signup(signUpRequest);

        return ResponseEntity.ok().body("Welcome!");
    }
}
