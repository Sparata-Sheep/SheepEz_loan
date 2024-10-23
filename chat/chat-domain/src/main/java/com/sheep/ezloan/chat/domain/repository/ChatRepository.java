package com.sheep.ezloan.chat.domain.repository;

import com.sheep.ezloan.chat.domain.model.Chat;
import java.util.UUID;
import org.springframework.data.jpa.repository.Query;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ChatRepository {

    Mono<Chat> save(Chat chatEntity);

    Flux<Chat> findAll();

    Mono<Chat> findByUuid(UUID chatUuid);

    // participants 리스트에 해당 사용자가 포함된 채팅방을 조회하는 쿼리
    @Query("SELECT c FROM ChatEntity c WHERE :participantId MEMBER OF c.participants")
    Flux<Chat> findByParticipantId(Long participantId); // 사용자가 포함된 채팅방만 조회
}
