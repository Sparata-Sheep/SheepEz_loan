package com.sheep.ezloan.contact.storage.entity;

import com.sheep.ezloan.contact.domain.model.ContractResult;
import com.sheep.ezloan.contact.domain.model.ContractStatus;
import com.sheep.ezloan.support.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
@Builder
@Table(name = "p_contracts")
public class ContractEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "contract_uuid")
    private UUID contractUuid;

    @Column(name = "post_uuid")
    private UUID postUuid;

    @Column(name = "request_user_id", nullable = false)
    private Long requestUserId;

    @Column(name = "receive_user_id", nullable = false)
    private Long receiveUserId;

    @Column(name = "request_username", nullable = false)
    private String requestUsername;

    @Column(name = "receive_username", nullable = false)
    private String receiveUsername;

    @Column(name = "is_accepted", nullable = false)
    private Boolean isAccepted;

    @Column(name = "requester_is_completed", nullable = false)
    private Boolean requesterIsCompleted;

    @Column(name = "receiver_is_completed", nullable = false)
    private Boolean receiverIsCompleted;

    @Column(name = "requester_is_canceled", nullable = false)
    private Boolean requesterIsCanceled;

    @Column(name = "receiver_is_canceled", nullable = false)
    private Boolean receiverIsCanceled;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ContractStatus status;

    public ContractEntity(UUID postUuid, Long requestUserId, Long receiveUserId, String requestUsername,
            String receiveUsername) {
        this.postUuid = postUuid;
        this.requestUserId = requestUserId;
        this.receiveUserId = receiveUserId;
        this.requestUsername = requestUsername;
        this.receiveUsername = receiveUsername;
        this.isAccepted = false;
        this.requesterIsCompleted = false;
        this.receiverIsCompleted = false;
        this.requesterIsCanceled = false;
        this.receiverIsCanceled = false;
        this.status = ContractStatus.WAITING;
    }

    public ContractResult toDomain() {
        return ContractResult.builder()
            .contractUuid(contractUuid)
            .postUuid(postUuid)
            .requestUserId(requestUserId)
            .receiveUserId(receiveUserId)
            .requestUsername(requestUsername)
            .receiveUsername(receiveUsername)
            .isAccepted(isAccepted)
            .requesterIsCompleted(requesterIsCompleted)
            .receiverIsCompleted(receiverIsCompleted)
            .requesterIsCanceled(requesterIsCanceled)
            .receiverIsCanceled(receiverIsCanceled)
            .status(status)
            .build();
    }

    public void accept() {
        isAccepted = true;
        status = ContractStatus.IN_PROGRESS;
    }

    public void requesterComplete() {
        requesterIsCompleted = true;
        if (receiverIsCompleted)
            status = ContractStatus.COMPLETED;
    }

    public void receiverComplete() {
        receiverIsCompleted = true;
        if (requesterIsCompleted)
            status = ContractStatus.COMPLETED;
    }

    public void requesterCancel() {
        requesterIsCanceled = true;
        if (receiverIsCanceled)
            status = ContractStatus.CANCELED;
    }

    public void receiverCancel() {
        receiverIsCanceled = true;
        if (requesterIsCanceled)
            status = ContractStatus.CANCELED;
    }

}
