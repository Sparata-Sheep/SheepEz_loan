package com.sheep.ezloan.contact.api.controller.dto;

import com.sheep.ezloan.contact.domain.model.ContractResult;
import com.sheep.ezloan.contact.domain.model.ContractStatus;
import com.sheep.ezloan.support.model.DomainPage;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public interface ContractDto {

    @Data
    @Builder
    class Request {

        private final UUID postUuid;

        private final Long requestUserId;

        private final Long receiveUserId;

    }

    @Data
    @Builder
    class DeleteResponse {

        private final UUID contractUuid;

        public static ContractDto.DeleteResponse of(UUID contractUuid) {
            return DeleteResponse.builder().contractUuid(contractUuid).build();
        }

    }

    @Data
    @Builder
    class Response {

        private final UUID contractUuid;

        private final UUID postUuid;

        private final Long requestUserId;

        private final Long receiveUserId;

        private final String requestUsername;

        private final String receiveUsername;

        private final boolean isAccepted;

        private final boolean requesterIsCompleted;

        private final boolean receiverIsCompleted;

        private final boolean requesterIsCanceled;

        private final boolean receiverIsCanceled;

        private final ContractStatus status;

        public static Response of(ContractResult contractResult) {
            return Response.builder()
                .contractUuid(contractResult.getContractUuid())
                .postUuid(contractResult.getPostUuid())
                .requestUserId(contractResult.getRequestUserId())
                .receiveUserId(contractResult.getReceiveUserId())
                .requestUsername(contractResult.getRequestUsername())
                .receiveUsername(contractResult.getReceiveUsername())
                .isAccepted(contractResult.isAccepted())
                .requesterIsCompleted(contractResult.isRequesterIsCompleted())
                .receiverIsCompleted(contractResult.isReceiverIsCompleted())
                .requesterIsCanceled(contractResult.isRequesterIsCanceled())
                .receiverIsCanceled(contractResult.isReceiverIsCanceled())
                .status(contractResult.getStatus())
                .build();
        }

        public static List<Response> of(DomainPage<ContractResult> contracts) {
            return contracts.getData().stream().map(Response::of).collect(Collectors.toList());
        }

    }

}
