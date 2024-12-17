package backend.mbti.controller;

import backend.mbti.dto.SignDto;
import backend.mbti.service.SignService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = "http://localhost:3000", exposedHeaders = "Authorization")
public class SignController {
    private final SignService signService;

    @PostMapping("/signin")
    public ResponseEntity<String> login(@RequestBody SignDto.LoginRequest loginRequest) {
        String token = signService.validToSign(loginRequest);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + token);
        return ResponseEntity.ok().headers(headers).build();
    }

    @PostMapping("/signup")
    public ResponseEntity<?> sign(@RequestBody SignDto.SignUpRequest signUpRequest) {
        signService.signup(signUpRequest);
        return ResponseEntity.ok().body("Welcome!");
    }
}
