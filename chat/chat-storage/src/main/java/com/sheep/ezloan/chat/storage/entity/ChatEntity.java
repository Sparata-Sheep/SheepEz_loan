package com.sheep.ezloan.chat.storage.entity;

import com.sheep.ezloan.chat.domain.model.Chat;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.List;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "p_chattings")
public class ChatEntity {

    @Id
    @GeneratedValue
    @Column(name = "chatting_uuid", nullable = false, unique = true)
    private UUID uuid;

    @Column(name = "post_uuid", nullable = false)
    private UUID postUuid;

    @Column(name = "creditor_id", nullable = false)
    private Long creditorId;

    @Column(name = "debtor_id", nullable = false)
    private Long debtorId;

    @Column(name = "creator_name", nullable = false)
    private String creatorName;

    @Column(name = "exist_lawyer", nullable = false)
    private Boolean existLawyer;

    @ElementCollection // 리스트 필드를 JPA가 관리할 수 있게 하는 어노테이션
    private List<Long> participants; // 채팅 참여자 목록

    @Column(name = "lawyer_id")
    private Long lawyerId;

    @Column(name = "is_delete", nullable = false)
    private boolean isDelete = false;

    public Chat toDomain() {
        return Chat.builder()
            .uuid(uuid)
            .postUuid(postUuid)
            .creditorId(creditorId)
            .debtorId(debtorId)
            .creatorName(creatorName)
            .existLawyer(existLawyer)
            .lawyerId(lawyerId)
            .build();
    }

}
