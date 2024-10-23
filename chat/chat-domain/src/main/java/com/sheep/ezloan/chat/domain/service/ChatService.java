package com.sheep.ezloan.chat.domain.service;

import com.sheep.ezloan.chat.domain.client.PostClient;
import com.sheep.ezloan.chat.domain.model.Chat;
import com.sheep.ezloan.chat.domain.repository.ChatRepository;
import com.sheep.ezloan.user.storage.entity.RoleType;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatRepository chatRepository;

    private final PostClient postClient;

    private final Long userId;

    private final RoleType roleType;

    // 채팅방 생성
    public Mono<Chat> createChatRoom(Long userId, UUID postUuid) {
        if (roleType == RoleType.LAWYER) {
            return Mono.error(new IllegalStateException("변호사는 채팅방을 생성할 수 없습니다."));
        }

        // Feign Client를 통해 contact-api에서 게시글 정보 가져오기
        return postClient.getPostById(postUuid).flatMap(post -> {
            // loan_type에 따라 게시글 작성자와 유저의 역할을 결정
            Long creditorId;
            Long debtorId;
            String creatorName;

            if (post.getLoanType().equals("GIVE")) {
                creditorId = post.getUserId(); // 게시글 작성자가 채권자
                debtorId = userId; // 현재 요청한 사용자가 채무자
                creatorName = post.getUsername(); // 채권자의 이름을 저장
            }
            else if (post.getLoanType().equals("TAKE")) {
                creditorId = userId; // 현재 요청한 사용자가 채권자
                debtorId = post.getUserId(); // 게시글 작성자가 채무자
                creatorName = post.getUsername(); // 채무자의 이름을 저장
            }
            else {
                return Mono.error(new IllegalStateException("유효하지 않은 loan_type 입니다."));
            }

            // 채팅 참여자 리스트 생성 (채권자와 채무자 추가)
            List<Long> participants = new ArrayList<>();
            participants.add(creditorId);
            participants.add(debtorId);

            // Chat 객체 생성 (Builder 패턴 사용)
            Chat chat = Chat.builder()
                .postUuid(postUuid)
                .creditorId(creditorId)
                .debtorId(debtorId)
                .creatorName(creatorName)
                .existLawyer(false) // 기본값 설정
                .lawyerId(null) // 기본값 설정
                .participants(participants) // 참여자 리스트 설정
                .build();

            // 채팅방을 저장하고 반환
            return chatRepository.save(chat);
        });
    }

    // 채팅방 전체 조회
    public Flux<Chat> getAllChatRooms() {
        if (roleType == RoleType.MASTER) {
            // 관리자는 모든 채팅방 조회 가능
            return chatRepository.findAll();
        }
        else {
            // 일반 사용자와 변호사는 자신이 포함된 채팅방만 조회 가능
            return chatRepository.findByParticipantId(userId);
        }
    }

    // 채팅방 단건 조회
    public Mono<Chat> getChatById(UUID chatUuid) {
        if (roleType == RoleType.MASTER) {
            // 관리자는 모든 채팅방 조회 가능
            return chatRepository.findByUuid(chatUuid);
        }
        else {
            // 일반 사용자와 변호사는 자신이 속한 채팅방만 조회 가능
            return chatRepository.findByUuid(chatUuid)
                .filter(chat -> chat.getParticipants().contains(userId))
                .switchIfEmpty(Mono.error(new IllegalStateException("권한이 없습니다.")));
        }
    }

    // 채팅방 변호사 초대
    public Mono<Chat> inviteChat(UUID chatUuid, Long lawyerId) {
        return chatRepository.findByUuid(chatUuid).flatMap(chat -> {
            if (!chat.getParticipants().contains(lawyerId)) {
                chat.getParticipants().add(lawyerId);
            }
            chat.setLawyerId(lawyerId); // 변호사 ID 설정
            chat.setExistLawyer(true);
            return chatRepository.save(chat); // 저장
        });
    }

    // 채팅방 참여자 목록 조회
    public Mono<List<Long>> getChatParticipants(UUID chatUuid) {
        return chatRepository.findByUuid(chatUuid)
            .map(Chat::getParticipants) // 참여자 목록 반환
            .defaultIfEmpty(new ArrayList<>()); // 없을 경우 빈 리스트 반환
    }

}
