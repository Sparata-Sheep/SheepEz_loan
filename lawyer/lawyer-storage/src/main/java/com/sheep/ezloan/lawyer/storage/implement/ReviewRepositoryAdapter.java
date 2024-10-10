package com.sheep.ezloan.lawyer.storage.implement;

import java.util.UUID;

import jakarta.persistence.EntityNotFoundException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sheep.ezloan.lawyer.domain.model.Review;
import com.sheep.ezloan.lawyer.domain.repository.ReviewRepository;
import com.sheep.ezloan.lawyer.storage.entity.ReviewEntity;
import com.sheep.ezloan.support.model.DomainPage;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ReviewRepositoryAdapter implements ReviewRepository {

    private final ReviewJpaRepository reviewJpaRepository;

    @Override
    public Review save(Review review) {
        ReviewEntity reviewEntity = ReviewEntity.builder()
            .lawyerId(review.getLawyer())
            .reviewerId(review.getReviewer())
            .title(review.getTitle())
            .content(review.getContent())
            .score(review.getScore())
            .build();
        return reviewJpaRepository.save(reviewEntity).toDomain();
    }

    @Override
    public Review findByUuid(UUID uuid) {
        return reviewJpaRepository.findById(uuid).map(ReviewEntity::toDomain).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    @Transactional
    public Review update(Review modifiedReview) {
        ReviewEntity targetReviewEntity = reviewJpaRepository.findById(modifiedReview.getUuid())
            .orElseThrow(EntityNotFoundException::new);
        ReviewEntity modifiedReviewEntity = targetReviewEntity.update(modifiedReview.getTitle(),
                modifiedReview.getContent(), modifiedReview.getScore());

        return modifiedReviewEntity.toDomain();
    }

    @Override
    @Transactional
    public Review delete(Review targetReview) {
        ReviewEntity targetReviewEntity = reviewJpaRepository.findById(targetReview.getUuid())
            .orElseThrow(EntityNotFoundException::new);
        targetReviewEntity.delete();

        return targetReviewEntity.toDomain();
    }

    @Override
    public DomainPage<Review> getReviewsByLawyerId(Long lawyerId, String sortBy, String direction, int page, int size) {
        Sort sort = Sort.by(Sort.Direction.fromString(direction), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Review> reviewPage = reviewJpaRepository.getReviewsBySorting(lawyerId, pageable)
            .map(ReviewEntity::toDomain);

        return DomainPage.of(reviewPage.getContent(), reviewPage.getTotalElements(), reviewPage.getTotalPages(),
                reviewPage.getNumber(), reviewPage.getNumber(), reviewPage.hasNext());
    }

}
