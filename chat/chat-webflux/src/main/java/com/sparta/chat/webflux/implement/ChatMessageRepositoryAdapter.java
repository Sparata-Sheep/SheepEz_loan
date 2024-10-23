package com.sparta.chat.webflux.implement;

import com.sheep.ezloan.chat.domain.model.ChatMessage;
import com.sheep.ezloan.chat.domain.repository.ChatMessageRepository;
import com.sparta.chat.webflux.entity.ChatMessageEntity;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
public class ChatMessageRepositoryAdapter implements ChatMessageRepository {

    private final ChatReactiveMongoRepository chatReactiveMongoRepository;

    @Override
    public Mono<ChatMessage> save(ChatMessage chatMessage) {
        ChatMessageEntity chatMessageEntity = ChatMessageEntity.builder()
            .chatUuid(chatMessage.getChatUuid())
            .senderUserName(chatMessage.getSenderUserName())
            .content(chatMessage.getContent())
            .timestamp(chatMessage.getTimestamp())
            .build();

        return chatReactiveMongoRepository.save(chatMessageEntity).map(ChatMessageEntity::toDomain);
    }

    @Override
    public Flux<ChatMessage> findByChatUuid(UUID chatUuid) {
        return chatReactiveMongoRepository.findByChatUuid(chatUuid);
    }

}
