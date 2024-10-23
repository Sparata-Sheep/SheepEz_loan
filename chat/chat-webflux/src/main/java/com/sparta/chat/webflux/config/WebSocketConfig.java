package com.sparta.chat.webflux.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sheep.ezloan.chat.domain.repository.ChatMessageRepository;
import com.sparta.chat.webflux.handler.ChatWebSocketHandler;
import com.sparta.chat.webflux.producer.ChatMessageProducer;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    private final ChatMessageRepository chatMessageRepository;

    private final ObjectMapper objectMapper;

    private final ChatMessageProducer chatMessageProducer; // Producer 필드 추가

    public WebSocketConfig(ChatMessageRepository chatMessageRepository, ObjectMapper objectMapper,
            ChatMessageProducer chatMessageProducer) {
        this.chatMessageRepository = chatMessageRepository;
        this.objectMapper = objectMapper;
        this.chatMessageProducer = chatMessageProducer; // Producer 주입
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(new ChatWebSocketHandler(chatMessageRepository, objectMapper, chatMessageProducer), "/chat")
            .setAllowedOrigins("*");
    }

}
