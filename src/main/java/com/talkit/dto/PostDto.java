package com.talkit.dto;

import lombok.*;

import java.util.Date;
import java.util.List;

public class PostDto {
    @Data
    public static class CreateRequest {
        private String title;
        private String optionA;
        private String optionB;
    }

    @Data
    @AllArgsConstructor
    public static class CreateResponse {
        private Long postId;
        private Long memberId;
        private String username;
        private String mbtiType;
        private String profileImage;
        private String title;
        private String optionA;
        private String optionB;
        private Long countComment;
        private Date createAt;
        private Long view;
        private Long like;
    }

    @Data
    @AllArgsConstructor
    public static class Response {
        private Long postId;
        private Long memberId;
        private String username;
        private String mbti;
        private String profileImage;
        private String title;
        private String optionA;
        private String optionB;
        private Date createAtPost;
        private Long view;
        private Long like;
        private Long countComment;
        private Long countCommentByOptionA;
        private Long countCommentByOptionB;
        private List<CommentDto.GetResponse> commentList;
        private CommentDto.getTopCommentA topCommentA;
        private CommentDto.getTopCommentB topCommentB;
    }

    @Data
    @AllArgsConstructor
    public static class ListResponse {
        private Long postId;
        private Long memberId;
        private String username;
        private String mbtiType;
        private String profileImage;
        private String title;
        private String optionA;
        private String optionB;
        private Long countComment;
        private Date createAt;
        private Long view;
        private Long like;
    }

    @Data
    public static class DeleteRequest {
        private Long postId;
    }

    @Data
    public static class UpdateRequest {
        private Long postId;
        private String title;
        private String optionA;
        private String optionB;
    }

    @Data
    public static class LikePostRequest {
        private Long postId;
    }

    @Data
    public static class logicResponse {
        private Long postId;
        private String title;
        private String optionA;
        private String optionB;
        private Long countByOptionA;
        private Long countByOptionB;
    }
}
