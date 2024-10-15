package com.sheep.ezloan.lawyer.domain.model;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class Review {

    private UUID uuid;

    private Long lawyer;

    private Long reviewer;

    private String title;

    private String content;

    private Integer score;

    public Review modify(String title, String content, Integer score) {
        this.title = title;
        this.content = content;
        this.score = score;
        return this;
    }

}
