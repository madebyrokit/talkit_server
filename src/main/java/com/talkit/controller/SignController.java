package com.talkit.controller;

import com.talkit.configuration.jwt.JwtProvider;
import com.talkit.dto.SignDto;
import com.talkit.service.SignService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@Slf4j
@RequiredArgsConstructor
public class SignController {
    private final SignService signService;
    private final JwtProvider jwtProvider;

    @PostMapping("/register")
    public ResponseEntity<?> sign(@RequestBody SignDto.SignUpRequest signUpRequest) {
        signService.signup(signUpRequest);
        return ResponseEntity.ok().body("OK");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody SignDto.LoginRequest loginRequest) {
        String token = signService.getSign(loginRequest);
        HttpHeaders headers = new HttpHeaders();
        headers.add("authorization", "Bearer " + token);
        return ResponseEntity.ok().headers(headers).build();
    }
}
