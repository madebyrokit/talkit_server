package com.talkit.dto;

import lombok.Data;

public class MemberDto {
    @Data
    public static class Request {

    }

    @Data
    public static class Response {
        private Long id;
        private String email;
        private String username;
        private String mbtiType;
        private String profileImage;
    }
}
