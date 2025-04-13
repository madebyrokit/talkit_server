package com.talkit.dto;

import lombok.*;

import java.util.Date;

public class PostDto {
    @Data
    public static class CreatedPostRequest {
        private String title;
        private String opinionA;
        private String opinionB;
    }

    @Data
    @AllArgsConstructor
    public static class PostResponse {
        private Long post_id;
        private Long member_id;
        private String username;
        private String mbti_type;
        private String avatar;
        private String title;
        private String opinion_a;
        private String opinion_b;
        private Date created_at;
        private Long total_views;
        private Long total_likes;
        private Long total_comments;
    }

    @Data
    @AllArgsConstructor
    public static class Response {
        private Long post_id;
        private Long member_id;
        private String username;
        private String mbti_type;
        private String avatar;
        private String title;
        private String opinion_a;
        private String opinion_b;
        private Date created_at;
        private Long view;
        private Long like;
        private Long total_comments;
        private Long total_opinion_a;
        private Long total_opinion_b;
        private CommentDto.getTopCommentA top_comment_a;
        private CommentDto.getTopCommentB top_comment_b;
    }

    @Data
    public static class UpdateRequest {
        private Long post_id;
        private String title;
        private String opinion_a;
        private String opinion_b;
    }

    @Data
    @AllArgsConstructor
    public static class UpdateResponse {
        private Long post_id;
        private String title;
        private String opinion_a;
        private String opinion_b;
    }

    @Data
    public static class LikePostRequest {
        private Long post_id;
    }

    @Data
    public static class logicResponse {
        private Long post_id;
        private String title;
        private String opinion_a;
        private String opinion_b;
        private Long total_opinion_a;
        private Long total_opinion_b;
    }
}
