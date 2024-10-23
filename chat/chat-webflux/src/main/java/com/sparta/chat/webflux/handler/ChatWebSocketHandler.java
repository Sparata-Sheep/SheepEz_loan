package com.sparta.chat.webflux.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sheep.ezloan.chat.domain.model.ChatMessage;
import com.sheep.ezloan.chat.domain.repository.ChatMessageRepository;
import com.sparta.chat.webflux.producer.ChatMessageProducer;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import reactor.core.publisher.Sinks;

public class ChatWebSocketHandler extends TextWebSocketHandler {

    private static final Set<WebSocketSession> sessions = new CopyOnWriteArraySet<>();

    private final Sinks.Many<String> sink = Sinks.many().multicast().directBestEffort();

    private final ChatMessageRepository chatMessageRepository;

    private final ObjectMapper objectMapper;

    private final ChatMessageProducer chatMessageProducer; // Producer 필드 추가

    public ChatWebSocketHandler(ChatMessageRepository chatMessageRepository, ObjectMapper objectMapper,
            ChatMessageProducer chatMessageProducer) {
        this.chatMessageRepository = chatMessageRepository;
        this.objectMapper = objectMapper;
        this.chatMessageProducer = chatMessageProducer; // Producer 주입
        sink.asFlux().subscribe(this::sendMessageToAll);
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        sessions.add(session);
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) {
        sink.tryEmitNext(message.getPayload());

        try {
            ChatMessage chatMessage = objectMapper.readValue(message.getPayload(), ChatMessage.class);
            chatMessage.setTimestamp(LocalDateTime.now());
            chatMessageRepository.save(chatMessage).subscribe();

            // Kafka로 메시지 전송
            chatMessageProducer.sendMessage(String.valueOf(chatMessage.getChatUuid()), chatMessage); // chattingUuid를
                                                                                                     // 토픽으로
                                                                                                     // 사용
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        sessions.remove(session);
    }

    private void sendMessageToAll(String message) {
        for (WebSocketSession session : sessions) {
            if (session.isOpen()) {
                try {
                    session.sendMessage(new TextMessage(message));
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
