package com.june.chatting.domain.chat.model;

import lombok.Getter;

import java.io.Serializable;
import java.util.UUID;

@Getter
public class ChatRoom implements Serializable {
    private String roomId;
    private String name;

    public ChatRoom(String name) {
        this.roomId = UUID.randomUUID().toString();
        this.name = name;
    }
}
