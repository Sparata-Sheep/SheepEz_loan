package com.sparta.chat.webflux.producer;

import com.sheep.ezloan.chat.domain.model.ChatMessage;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class ChatMessageProducer {

    private final KafkaTemplate<String, ChatMessage> kafkaTemplate;

    public ChatMessageProducer(KafkaTemplate<String, ChatMessage> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(String chatRoomId, ChatMessage message) {
        kafkaTemplate.send(chatRoomId, message);
    }

}
