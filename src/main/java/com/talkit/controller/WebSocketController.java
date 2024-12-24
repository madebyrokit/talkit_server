package com.talkit.controller;

import com.talkit.dto.WebSocketDto;
import com.talkit.service.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/chat")
@RequiredArgsConstructor
@Slf4j
public class WebSocketController {
    private final ChatService chatService;
    private Map<String,String> session = new HashMap<>();

    @EventListener
    public void handleWebSocketConnectListener(SessionConnectEvent event) {
        StompHeaderAccessor headerAccesor = StompHeaderAccessor.wrap(event.getMessage());
        String sessionId = headerAccesor.getSessionId();
        log.info("세션 연결함: {}", sessionId);
    }

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccesor = StompHeaderAccessor.wrap(event.getMessage());
        String sessionId = headerAccesor.getSessionId();
        log.info("세션 연결 끊음: {}", sessionId);
    }
    @GetMapping
    public ResponseEntity<List<WebSocketDto.Response>> getChatList() {
        return ResponseEntity.ok().body(chatService.getChatList());
    }

    @MessageMapping("/message")
    @SendTo("/topic/message")
    public WebSocketDto.Response sendMessage(WebSocketDto.Request message) {
        chatService.saveChat(message);
        return new WebSocketDto.Response(
                message.getUuid(),
                message.getUsername(),
                message.getContent());
    }
}


