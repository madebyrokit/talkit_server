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

@Slf4j
@RequiredArgsConstructor
@RestController
public class SignController {
    private final SignService signService;

    @PostMapping("/register")
    public ResponseEntity<String> sign(@RequestBody SignDto.SignUpRequest signUpRequest) {
        String email = signService.register(signUpRequest);
        return ResponseEntity.ok().body(email);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody SignDto.LoginRequest loginRequest) {
        String token = signService.login(loginRequest);
        HttpHeaders headers = new HttpHeaders();
        headers.add("authorization", "Bearer " + token);
        return ResponseEntity.ok().headers(headers).build();
    }
}
