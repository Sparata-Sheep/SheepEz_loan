package com.sheep.ezloan.contact.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class Post {

    private Long userId;

    private String username;

    private String title;

    private String content;

    private LoanType loanType;

}
