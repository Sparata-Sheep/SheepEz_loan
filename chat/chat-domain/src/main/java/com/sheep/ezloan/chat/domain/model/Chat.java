package com.sheep.ezloan.chat.domain.model;

import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class Chat {

    private UUID uuid;

    private UUID postUuid;

    private Long creditorId;

    private Long debtorId;

    private String creatorName;

    private Boolean existLawyer = false;

    private List<Long> participants;

    private Long lawyerId;

}
