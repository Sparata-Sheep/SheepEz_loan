package com.sheep.ezloan.lawyer.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateReviewRequestDto(

        @NotNull(message = "userId is required") Long userId,

        @NotNull(message = "lawyerId is required") Long lawyerId,

        @NotBlank(message = "title is required") String title,

        @NotBlank(message = "content is required") String content,

        @NotNull(message = "score is required") Integer score

) {
}
