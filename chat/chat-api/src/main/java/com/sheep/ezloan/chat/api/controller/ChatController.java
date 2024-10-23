package com.sheep.ezloan.chat.api.controller;

import com.sheep.ezloan.chat.api.controller.dto.ChatResponseDto;
import com.sheep.ezloan.chat.domain.model.Chat;
import com.sheep.ezloan.chat.domain.service.ChatService;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/chat")
public class ChatController {

    private final ChatService chatService;

    // 채팅방 생성
    @PostMapping("/{postUuid}/rooms")
    public Mono<ResponseEntity<UUID>> createChatRoom(@RequestHeader("X-User-Id") Long userId,
            @PathVariable UUID postUuid) {
        return chatService.createChatRoom(userId, postUuid)
            .map(chat -> ResponseEntity.ok(chat.getUuid()))
            .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    // 채팅방 전체 조회
    @GetMapping("/rooms")
    public Flux<Chat> getAllChatRooms() {
        return chatService.getAllChatRooms();
    }

    // 채팅방 단건 조회
    @GetMapping("/rooms/{chatUuid}")
    public Mono<Chat> getChatById(@PathVariable UUID chatUuid) {
        return chatService.getChatById(chatUuid);
    }

    // 채팅방 변호사 초대
    @PostMapping("/rooms/{chatUuid}/invite/{lawyersId}")
    @ResponseBody
    public Mono<ResponseEntity<ChatResponseDto>> inviteChat(@PathVariable UUID chatUuid, @PathVariable Long lawyersId) {
        return chatService.inviteChat(chatUuid, lawyersId)
            .map(chat -> ResponseEntity.ok(new ChatResponseDto(chat.getUuid(), chat.getParticipants())))
            .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    // 채팅방 참여자 목록 조회
    @GetMapping("/rooms/{chatUuid}/participants")
    public Mono<ResponseEntity<List<Long>>> getChatParticipants(@PathVariable UUID chatUuid) {
        return chatService.getChatParticipants(chatUuid)
            .map(ResponseEntity::ok)
            .defaultIfEmpty(ResponseEntity.notFound().build());
    }

}
