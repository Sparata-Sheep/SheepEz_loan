package com.sheep.ezloan.lawyer.api.dto;

import java.util.List;

import com.sheep.ezloan.lawyer.domain.model.Lawyer;

public record LawyerResponse(Long id, String nickname, String name, String email, String introduction,
        String certificationInfo, String career, List<ReviewResponse> reviewResponseList) {

    public static LawyerResponse of(Lawyer lawyer) {
        return new LawyerResponse(lawyer.getId(), lawyer.getNickname(), lawyer.getName(), lawyer.getEmail(),
                lawyer.getIntroduction(), lawyer.getCertificationInfo(), lawyer.getCareer(),
                lawyer.getReviewList().stream().map(ReviewResponse::of).toList());
    }
}
