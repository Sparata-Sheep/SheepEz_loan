package com.sheep.ezloan.chat.storage.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "p_chattings")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Chat {

    @Id
    @Column(name = "chatting_uuid", nullable = false, unique = true)
    private UUID uuid;

    @Column(name = "post_uuid", nullable = false)
    private UUID postUuid;

    @Column(name = "creditor_id", nullable = false)
    private Long creditorId;

    @Column(name = "debtor_id", nullable = false)
    private Long debtorId;

    @Column(name = "exist_lawyer", nullable = false)
    private Boolean existLawyer;

    @Column(name = "lawyer_id")
    private Long lawyerId;

    @Column(name = "is_delete", nullable = false)
    private boolean isDelete = false;
}
