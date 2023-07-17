package com.june.chatting.domain.chat.service;

import com.june.chatting.domain.chat.model.ChatMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatMessageService {
    private final SimpMessageSendingOperations messagingTemplate;

    public void sendMessage(ChatMessage chatMessage) {  // Websocket 구독자에게 채팅 메시지 Send
        messagingTemplate.convertAndSend("/sub/chat/room/" + chatMessage.getRoomId(), chatMessage);
    }
}
