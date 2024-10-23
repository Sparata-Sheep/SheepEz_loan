package com.sheep.ezloan.chat.domain.repository;

import com.sheep.ezloan.chat.domain.model.ChatMessage;
import java.util.UUID;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface ChatMessageRepository {

    Mono<ChatMessage> save(ChatMessage chatMessage);

    Flux<ChatMessage> findByChatUuid(UUID chatUuid);

}
