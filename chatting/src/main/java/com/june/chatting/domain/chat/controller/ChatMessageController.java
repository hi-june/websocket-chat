package com.june.chatting.domain.chat.controller;

import com.june.chatting.domain.chat.model.ChatMessage;
import com.june.chatting.domain.chat.service.ChatMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class ChatMessageController {
    private final ChatMessageService chatMessageService;

    @MessageMapping("/chat/message")
    public void handleMessage(ChatMessage chatMessage) {
        switch (chatMessage.getType()) {    // 입장, 퇴장 message 처리
            case ENTER: {
                chatMessage.setMessage(chatMessage.getSender() + "님 등장!");
                break;
            }
            case EXIT: {
                chatMessage.setMessage(chatMessage.getSender() + "님 퇴장!");
            }
        }

        chatMessageService.sendMessage(chatMessage);
    }
}
