package com.sheep.ezloan.lawyer.domain.repository;

import java.util.UUID;

import com.sheep.ezloan.lawyer.domain.model.Review;
import com.sheep.ezloan.support.model.DomainPage;

public interface ReviewRepository {

    Review save(Review review);

    Review findByUuid(UUID uuid);

    Review update(Review review);

    Review delete(Review targetReview);

    DomainPage<Review> getReviewsByLawyerId(Long lawyerId, String sortBy, String direction, int page, int size);

}
