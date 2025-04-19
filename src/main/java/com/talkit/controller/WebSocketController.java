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

import java.util.List;

@RestController
@RequestMapping("/chat")
@RequiredArgsConstructor
@Slf4j
public class WebSocketController {
    private final ChatService chatService;


    @EventListener
    public ResponseEntity<WebSocketDto.Join> handleWebSocketConnectListener(SessionConnectEvent event) {
        StompHeaderAccessor stompHeaderAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String sessionId = stompHeaderAccessor.getSessionId();
        log.info("세션 연결함: {}", sessionId);
        WebSocketDto.Join join = new WebSocketDto.Join(sessionId); // test
        return ResponseEntity.ok().body(join);
    }

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor stompHeaderAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String sessionId = stompHeaderAccessor.getSessionId();
        log.info("세션 연결 끊음: {}", sessionId);
    }

    @GetMapping
    public ResponseEntity<List<WebSocketDto.Response>> getChatList() {
        List<WebSocketDto.Response> responseList = chatService.getChatList();
        return ResponseEntity.ok().body(responseList);
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


