package com.talkit.service;

import com.talkit.dto.SignDto;

public interface SignService {
    String getSign(SignDto.LoginRequest loginRequest);
    void signup(SignDto.SignUpRequest signUpRequest);
}
