package backend.mbti.service;

import backend.mbti.dto.SignDto;

public interface SignService {
    String validToSign(SignDto.LoginRequest loginRequest);
    void signup(SignDto.SignUpRequest signUpRequest);
}
