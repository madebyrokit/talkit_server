package com.talkit.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

public class WebSocketDto {
    @Data
    public static class Request {
        String uuid;
        String username;
        String content;
    }

    @Data
    @AllArgsConstructor
    public static class Response {
        String uuid;
        String username;
        String content;
    }
}
