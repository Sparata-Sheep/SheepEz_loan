package com.sheep.ezloan.contact.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
@Builder
public class ContractResult {

    private UUID contractUuid;

    private UUID postUuid;

    private Long requestUserId;

    private Long receiveUserId;

    private String requestUsername;

    private String receiveUsername;

    private boolean isAccepted;

    private boolean requesterIsCompleted;

    private boolean receiverIsCompleted;

    private boolean requesterIsCanceled;

    private boolean receiverIsCanceled;

    private ContractStatus status;

}
