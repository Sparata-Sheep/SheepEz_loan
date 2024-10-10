package com.sheep.ezloan.lawyer.domain.service;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.sheep.ezloan.lawyer.domain.model.Review;
import com.sheep.ezloan.lawyer.domain.repository.ReviewRepository;
import com.sheep.ezloan.support.model.DomainPage;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;

    public Review createReview(Long lawyerId, Long reviewerId, String title, String content, Integer score) {
        Review review = Review.builder()
            .lawyer(lawyerId)
            .reviewer(reviewerId)
            .title(title)
            .content(content)
            .score(score)
            .build();
        return reviewRepository.save(review);
    }

    public Review modifyReview(UUID targetUuid, String title, String content, Integer score) {
        Review targetReview = reviewRepository.findByUuid(targetUuid);
        Review modifiedReview = targetReview.modify(title, content, score);
        return reviewRepository.update(modifiedReview);
    }

    public DomainPage<Review> getReviews(Long lawyerId, String sortBy, String direction, Integer page, Integer size) {

        return reviewRepository.getReviewsByLawyerId(lawyerId, sortBy, direction, page, size);
    }

    public Review deleteReview(UUID reviewId) {
        Review targetReview = reviewRepository.findByUuid(reviewId);
        return reviewRepository.delete(targetReview);
    }

}
