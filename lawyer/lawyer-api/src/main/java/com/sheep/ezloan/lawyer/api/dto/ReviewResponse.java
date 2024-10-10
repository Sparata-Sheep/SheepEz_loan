package com.sheep.ezloan.lawyer.api.dto;

import java.util.UUID;

import com.sheep.ezloan.lawyer.domain.model.Review;
import com.sheep.ezloan.support.model.DomainPage;

public record ReviewResponse(UUID id, Long lawyerId, Long reviewerId, String title, String content, Integer score) {

    public static ReviewResponse of(Review review) {
        return new ReviewResponse(review.getUuid(), review.getLawyer(), review.getReviewer(), review.getTitle(),
                review.getContent(), review.getScore());
    }

    public static DomainPage<ReviewResponse> of(DomainPage<Review> reviewPage) {
        return DomainPage.of(reviewPage.getData().stream().map(ReviewResponse::of).toList(), reviewPage.getTotalItems(),
                reviewPage.getTotalPages(), reviewPage.getCurrentPage(), reviewPage.getPageSize(),
                reviewPage.getHasNext());
    }
}
