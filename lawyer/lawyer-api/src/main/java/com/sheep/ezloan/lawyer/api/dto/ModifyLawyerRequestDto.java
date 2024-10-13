package com.sheep.ezloan.lawyer.api.dto;

public record ModifyLawyerRequestDto(

        String nickname,

        String email,

        String introduction,

        String certificationInfo,

        String career

) {
}
