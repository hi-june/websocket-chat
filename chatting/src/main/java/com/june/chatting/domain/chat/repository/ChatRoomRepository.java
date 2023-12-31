package com.june.chatting.domain.chat.repository;

import com.june.chatting.domain.chat.model.ChatRoom;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ChatRoomRepository {
    private static final String CHAT_ROOMS = "CHAT_ROOM";

    private final RedisTemplate<String, Object> redisTemplate;  // redis
    private HashOperations<String, String, ChatRoom> opsHashChatRoom;   // redis 저장

    @PostConstruct
    private void init() {
        opsHashChatRoom = redisTemplate.opsForHash();
    }

    // 채팅방 단일 조회
    public ChatRoom findRoomById(String id) {
        return opsHashChatRoom.get(CHAT_ROOMS, id);
    }

    // 채팅방 목록 조회
    public List<ChatRoom> findAllRoom() {
        return opsHashChatRoom.values(CHAT_ROOMS);
    }

    public ChatRoom createChatRoom(String name) {
        ChatRoom chatRoom = new ChatRoom(name);
        opsHashChatRoom.put(CHAT_ROOMS, chatRoom.getRoomId(), chatRoom);    // 채팅방 redis에 저장
        return chatRoom;
    }
}
