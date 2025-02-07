package com.talkit.dto;

import lombok.*;
import java.util.Date;

public class CommentDto{
    @Data
    public static class CreateRequest {
        private Long postId;
        private String content;
        private String opinion;
    }

    @Data
    @AllArgsConstructor
    public static class CreateResponse {
        private Long postId;
        private Long commentId;
        private String username;
        private String content;
        private String mbtiType;
        private String profileImage;
        private String option;
        private Long like;
        private Date createdAt;
    }

    @Data
    public static class GetResponse {
        private Long postId;
        private Long commentId;
        private String username;
        private String profileImage;
        private String mbtiType;
        private String content;
        private String option;
        private Long like;
        private Date createdAt;
    }

    @Data
    public static class DeleteRequest {
        private Long commentId;
    }

    @Data
    public static class UpdateRequest {
        private Long commentId;
        private String content;
        private String selectedOpinion;
    }
    @Data
    @AllArgsConstructor
    public static class UpdateResponse {
        private Long commentId;
        private String content;
        private String opinion;
    }

    @Data
    public static class LikeRequest {
        private Long commentId;
    }

    @Data
    public static class getTopCommentA {
        private Long commentId;
        private String username;
        private String mbtiType;
        private String profileImage;
        private String comment;
        private String selectedOption;
        private Long countLikeComment;
        private Date createdAtPost;
        private Date createdAtComment;
    }

    @Data
    public static class getTopCommentB {
        private Long commentId;
        private String username;
        private String mbtiType;
        private String profileImage;
        private String comment;
        private String selectedOption;
        private Long countLikeComment;
        private Date createdAtPost;
        private Date createdAtComment;
    }
}
