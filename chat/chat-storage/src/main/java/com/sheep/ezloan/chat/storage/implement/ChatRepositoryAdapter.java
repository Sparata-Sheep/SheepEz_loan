package com.sheep.ezloan.chat.storage.implement;

import com.sheep.ezloan.chat.domain.model.Chat;
import com.sheep.ezloan.chat.domain.repository.ChatRepository;
import com.sheep.ezloan.chat.storage.entity.ChatEntity;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Repository
@RequiredArgsConstructor
public class ChatRepositoryAdapter implements ChatRepository {

    private final JpaChatRepository jpaChatRepository;

    @Override
    public Mono<Chat> save(Chat chat) {
        return Mono.fromCallable(() -> {
            ChatEntity chatEntity = ChatEntity.builder()
                .postUuid(chat.getPostUuid())
                .creditorId(chat.getCreditorId())
                .debtorId(chat.getDebtorId())
                .creatorName(chat.getCreatorName())
                .existLawyer(chat.getExistLawyer())
                .lawyerId(chat.getLawyerId())
                .build();
            return jpaChatRepository.save(chatEntity);
        })
            .subscribeOn(Schedulers.boundedElastic()) // 블로킹 작업을 별도 스레드에서 처리
            .map(ChatEntity::toDomain);
    }

    @Override
    public Flux<Chat> findAll() {
        // return jpaChatRepository.findAll().map(ChatEntity::toDomain);

        // JPA 호출을 블로킹 방식으로 처리한 후 Flux로 변환
        return Flux.fromIterable(jpaChatRepository.findAll()).map(ChatEntity::toDomain);
    }

    @Override
    public Mono<Chat> findByUuid(UUID chatUuid) {
        return Mono.fromCallable(() -> jpaChatRepository.findById(chatUuid))
            .subscribeOn(Schedulers.boundedElastic()) // 블로킹 작업을 별도 스레드에서 처리
            .flatMap(optionalChatEntity -> optionalChatEntity.map(chatEntity -> Mono.just(chatEntity.toDomain()))
                .orElseGet(Mono::empty));
    }

    @Override
    public Flux<Chat> findByParticipantId(Long participantId) {
        return Flux.fromIterable(jpaChatRepository.findByParticipantId(participantId)) // 블로킹 호출을 리액티브로 변환
            .subscribeOn(Schedulers.boundedElastic()) // 블로킹 작업을 별도의 스레드에서 처리
            .map(ChatEntity::toDomain); // 엔티티를 도메인 객체로 변환
    }
}
