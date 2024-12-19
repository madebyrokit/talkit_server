package com.talkit.service;

import com.talkit.dto.SignDto;
import org.springframework.web.multipart.MultipartFile;

public interface SignService {
    String validatateFromSign(SignDto.LoginRequest loginRequest);
    void signup(SignDto.SignUpRequest signUpRequest);
}
