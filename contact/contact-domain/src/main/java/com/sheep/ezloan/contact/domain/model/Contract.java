package com.sheep.ezloan.contact.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
@Builder
public class Contract {

    private UUID postUuid;

    private Long requestUserId;

    private Long receiveUserId;

    private String requestUsername;

    private String receiveUsername;

}
