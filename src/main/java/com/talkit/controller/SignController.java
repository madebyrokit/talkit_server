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
@RequestMapping("/sign")
@Slf4j
@RequiredArgsConstructor
public class SignController {
    private final SignService signService;
    private final JwtProvider jwtProvider;

    @PostMapping("/up")
    public ResponseEntity<?> sign(@RequestBody SignDto.SignUpRequest signUpRequest) {
        log.info("가입");
        signService.signup(signUpRequest);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/in")
    public ResponseEntity<String> login(@RequestBody SignDto.LoginRequest loginRequest) {
        String token = signService.getSign(loginRequest);
        HttpHeaders headers = new HttpHeaders();
        headers.add("authorization", "Bearer " + token);
        return ResponseEntity.ok().headers(headers).build();
    }
}
