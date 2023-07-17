package com.june.chatting.domain.chat.model;

import lombok.Getter;

@Getter
public class ChatMessage {
    public enum MessageType {
        ENTER, TALK, EXIT
    }

    private MessageType type; // 메시지 타입
    private String roomId; // 방번호
    private String sender; // 메시지 보낸 사람
    private String message; // 메시지

    public void setMessage(String message) {
        this.message = message;
    }
}
