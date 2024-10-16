package com.sheep.ezloan.lawyer.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateLawyerRequestDto(

        @NotBlank(message = "nickname is required") String nickname,

        @NotBlank(message = "name is required") String name,

        @NotBlank(message = "email is required") String email,

        String introduction,

        @NotBlank(message = "certificationInfo is required") String certificationInfo,

        String career

) {
}
