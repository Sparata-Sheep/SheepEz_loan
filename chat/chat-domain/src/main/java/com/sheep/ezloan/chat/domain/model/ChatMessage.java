package com.sheep.ezloan.chat.domain.model;

import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChatMessage {

    private UUID uuid;

    private UUID chatUuid;

    private String senderUserName;

    private String content;

    private LocalDateTime timestamp;

}
