package com.sheep.ezloan.contact.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@AllArgsConstructor
@Builder
public class PostResult {

    private UUID postUuid;

    private Long userId;

    private String username;

    private String title;

    private String content;

    private PostStatus status;

    private LoanType loanType;

    private LocalDateTime createdAt;

}
