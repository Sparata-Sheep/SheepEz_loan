package com.sheep.ezloan.chat.domain.model;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class Post {

    private UUID postUuid;

    private Long userId;

    private String username;

    private String loanType;

}
