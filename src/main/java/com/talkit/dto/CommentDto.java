package com.talkit.dto;

import lombok.*;
import org.checkerframework.checker.units.qual.A;

import java.util.Date;

public class CommentDto{
    @Data
    public static class CreateRequest {
        private Long post_id;
        private String content;
        private String opinion;
    }

    @Data
    @AllArgsConstructor
    public static class CreateResponse {
        private Long post_id;
        private Long comment_id;
        private String username;
        private String content;
        private String mbti_type;
        private String avatar;
        private String opinion;
        private Long total_liked;
        private Date created_at;
    }

    @Data
    @AllArgsConstructor
    public static class Response {
        private Long post_id;
        private Long comment_id;
        private String content;
        private String opinion;
        private String username;
        private String avatar;
        private String mbti_type;
        private Long total_liked;
        private Date created_at;
    }

    @Data
    @AllArgsConstructor
    public static class Update {
        private Long comment_id;
        private String content;
        private String opinion;
    }

    @Data
    @AllArgsConstructor
    public static class TopCommentResponse {
        private Long comment_id;
        private String username;
        private String mbti_type;
        private String avatar;
        private String comment;
        private String opinion;
        private Long total_liked_comment;
        private Date created_at_comment;
    }
}
