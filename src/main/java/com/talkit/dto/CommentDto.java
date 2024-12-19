package com.talkit.dto;

import lombok.*;
import org.checkerframework.checker.units.qual.A;

import java.util.Date;
import java.util.List;

public class CommentDto{
    @Data
    public static class CreateRequest {
        private Long postId;
        private String content;
        private String option;
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
        private Date createAt;
    }

    @Data
    @AllArgsConstructor
    public static class GetResponse {
        private Long postId;
        private Long commentId;
        private String username;
        private String content;
        private String mbtiType;
        private String profileImage;
        private String option;
        private Long like;
        private Date postCreatAt;
        private Date createAt;
    }

    @Data
    public static class DeleteRequest {
        private Long commentId;
    }

    @Data
    public static class UpdateRequest {
        private Long postId;
        private String content;
        private String option;
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
        private Date createAtPost;
        private Date createAtComment;
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
        private Date createAtPost;
        private Date createAtComment;
    }
}
