package com.talkit.controller;

import com.talkit.configuration.jwt.JwtProvider;
import com.talkit.dto.SignDto;
import com.talkit.service.SignService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/sign")
@Slf4j
@RequiredArgsConstructor
public class SignController {
    private final SignService signService;

    @PostMapping("/up")
    public ResponseEntity<?> sign(@RequestBody SignDto.SignUpRequest signUpRequest) {
        signService.signup(signUpRequest);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/in")
    public ResponseEntity<String> login(@RequestBody SignDto.LoginRequest loginRequest) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("authorization", "Bearer " + signService.getSign(loginRequest));
        return ResponseEntity.ok().headers(headers).build();
    }
}
