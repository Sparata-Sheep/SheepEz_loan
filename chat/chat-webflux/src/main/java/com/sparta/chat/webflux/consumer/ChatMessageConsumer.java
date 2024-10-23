package com.sparta.chat.webflux.consumer;

import com.sheep.ezloan.chat.domain.model.ChatMessage;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class ChatMessageConsumer {

    @KafkaListener(topics = "chat_topic", groupId = "chat-group")
    public void listen(ChatMessage message) {
        System.out.println("Received: " + message);
    }

}
