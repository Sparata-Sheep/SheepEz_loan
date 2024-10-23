package com.sparta.chat.webflux.entity;

import com.sheep.ezloan.chat.domain.model.ChatMessage;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "p_chat_messages")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessageEntity {

    @Id
    @Column(name = "message_uuid", nullable = false, unique = true)
    private UUID uuid;

    @Column(name = "chat_uuid", nullable = false)
    private UUID chatUuid;

    @Column(name = "sender_name", nullable = false)
    private String senderUserName;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "timestamp", nullable = false)
    private LocalDateTime timestamp;

    public ChatMessage toDomain() {
        return ChatMessage.builder()
            .uuid(uuid)
            .chatUuid(chatUuid)
            .senderUserName(senderUserName)
            .content(content)
            .timestamp(timestamp)
            .build();
    }

}
