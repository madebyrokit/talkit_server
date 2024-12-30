package com.talkit.dto;

import lombok.Data;

import java.util.Date;

public class MemberDto {
    @Data
    public static class Request {
        private String email;
        private String username;
        private String password;
        private String confirmPassword;
        private String mbtiType;
    }

    @Data
    public static class Response {
        private Long id;
        private String email;
        private String username;
        private String mbtiType;
        private String profileImage;
    }
    @Data
    public static class ResponsePostList {
        private Long postId;
        private String title;
        private String opinionA;
        private String opinionB;
        private Date createdAt;
    }
    @Data
    public static class ResponseCommentList {
        private Long commentId;
        private String content;
        private String opinion;
        private Date createdAt;
    }

    @Data
    public static class ResponseLikedPostList {
        private Long postId;
        private String title;
        private Date createdAt;
    }
    @Data
    public static class ResponseLikedCommentList {
        private Long commentId;
        private String content;
        private Date createdAt;
    }
}
