package com.june.chatting.domain.chat.service;

import com.june.chatting.domain.chat.model.ChatRoom;
import com.june.chatting.domain.chat.repository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatRoomService {
    private final ChatRoomRepository chatRoomRepository;

    public ChatRoom createChatRoom(String roomName) {
        return chatRoomRepository.createChatRoom(roomName);
    }

    public ChatRoom findChatRoom(String roomId) {
        return chatRoomRepository.findRoomById(roomId);
    }

    public List<ChatRoom> findAllChatRooms() {
        return chatRoomRepository.findAllRoom();
    }
}
