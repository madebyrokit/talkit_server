package com.talkit.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

public class MemberDto {
    @Data
    public static class Register {
        private String email;
        private String username;
        private String password;
        private String confirmPassword;
        private String mbtiType;
    }

    @Data
    @AllArgsConstructor
    public static class Response {
        private Long id;
        private String email;
        private String username;
        private String avatar;
        private String mbti_type;
        private String o_auth_type;
    }
    @Data
    public static class ResponsePostList {
        private Long postId;
        private String title;
        private String opinion_a;
        private String opinion_b;
        private Date created_at;
    }
    @Data
    public static class ResponseCommentList {
        private Long comment_id;
        private String content;
        private String opinion;
        private Date created_at;
    }

    @Data
    public static class ResponseLikedPostList {
        private Long post_id;
        private String title;
        private Date created_at;
    }
    @Data
    public static class ResponseLikedCommentList {
        private Long comment_id;
        private String content;
        private Date created_at;
    }
}
