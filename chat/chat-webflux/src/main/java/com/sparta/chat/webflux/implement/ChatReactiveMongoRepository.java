package com.sparta.chat.webflux.implement;

import com.sheep.ezloan.chat.domain.model.ChatMessage;
import com.sparta.chat.webflux.entity.ChatMessageEntity;
import java.util.UUID;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface ChatReactiveMongoRepository extends ReactiveMongoRepository<ChatMessageEntity, UUID> {

    // 채팅 UUID로 메시지를 찾는 메서드
    Flux<ChatMessage> findByChatUuid(UUID chatUuid);

}
