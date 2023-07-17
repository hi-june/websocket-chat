package com.june.chatting.global.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    @Value("${stomp.allowed-origin-pattern}")
    private static String ALLOWED_ORIGIN_PATTERN;

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/sub");  // 메시지 구독 요청 prefix
        config.setApplicationDestinationPrefixes("/pub");   // 메시지 발행 요청 prefix
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {    // stomp websocket endpoint 및 CORS 설정
        registry.addEndpoint("/ws-stomp").setAllowedOriginPatterns(ALLOWED_ORIGIN_PATTERN).withSockJS();
    }
}
