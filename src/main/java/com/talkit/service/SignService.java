package com.talkit.service;

import com.talkit.dto.SignDto;

public interface SignService {
    String login(SignDto.LoginRequest loginRequest);
    String register(SignDto.SignUpRequest signUpRequest);
}
