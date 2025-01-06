package com.talkit.service;

import com.talkit.dto.WebSocketDto;
import com.talkit.entity.Chat;
import com.talkit.repository.ChatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class ChatService {
    private final ChatRepository chatRepository;

    public void saveChat(WebSocketDto.Request request) {
        Chat chat = new Chat();
        chat.setUuid(request.getUuid());
        chat.setUsername(request.getUsername());
        chat.setContent(request.getContent());
        chat.setCreatedAt(new Date());

        chatRepository.save(chat);
    }

    public List<WebSocketDto.Response> getChatList() {
        List<Chat> chatList = chatRepository.findTop20Chat();

        List<WebSocketDto.Response> responseList = new ArrayList<>();
        for (Chat chat : chatList) {
            WebSocketDto.Response response = new WebSocketDto.Response(
                    chat.getUuid(),
                    chat.getUsername(),
                    chat.getContent()
            );
            responseList.add(response);
        }
        Collections.reverse(responseList);
        return responseList;
    }

}
