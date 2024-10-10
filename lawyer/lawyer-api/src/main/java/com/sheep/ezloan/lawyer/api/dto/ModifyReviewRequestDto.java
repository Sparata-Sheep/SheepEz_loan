package com.sheep.ezloan.lawyer.api.dto;

public record ModifyReviewRequestDto(

        String title,

        String content,

        Integer score

) {
}
