package com.talkit.dto;

import lombok.*;

public class SignDto {
    @Data
    public static class LoginRequest {
        private String email;
        private String password;
    }
    @Data
    public static class SignUpRequest {
        private String email;
        private String password;
        private String confirm_password;
        private String username;
        private String mbti_type;
    }
}
