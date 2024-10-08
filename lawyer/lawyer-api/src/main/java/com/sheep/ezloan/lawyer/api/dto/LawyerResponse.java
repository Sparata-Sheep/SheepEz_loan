package com.sheep.ezloan.lawyer.api.dto;

import com.sheep.ezloan.lawyer.domain.model.Lawyer;

public record LawyerResponse(Long id, String nickname, String name, String email, String introduction,
        String certificationInfo, String career) {

    public static LawyerResponse of(Lawyer lawyer) {
        return new LawyerResponse(lawyer.getId(), lawyer.getNickname(), lawyer.getName(), lawyer.getEmail(),
                lawyer.getIntroduction(), lawyer.getCertificationInfo(), lawyer.getCareer());
    }
}
