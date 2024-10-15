package com.sheep.ezloan.lawyer.storage.implement;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.sheep.ezloan.lawyer.storage.entity.ReviewEntity;

public interface ReviewCustomRepository {

    Page<ReviewEntity> getReviewsBySorting(Long lawyerId, Pageable pageable);

}
