package com.sheep.ezloan.lawyer.api.controller;

import java.util.UUID;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sheep.ezloan.lawyer.api.dto.CreateReviewRequestDto;
import com.sheep.ezloan.lawyer.api.dto.ModifyReviewRequestDto;
import com.sheep.ezloan.lawyer.api.dto.ReviewResponse;
import com.sheep.ezloan.lawyer.domain.model.Review;
import com.sheep.ezloan.lawyer.domain.service.ReviewService;
import com.sheep.ezloan.support.model.DomainPage;
import com.sheep.ezloan.support.response.ApiResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping
    public ApiResponse<ReviewResponse> createReview(@RequestBody CreateReviewRequestDto request) {
        Review result = reviewService.createReview(request.lawyerId(), request.userId(), request.title(),
                request.content(), request.score());

        return ApiResponse.success(ReviewResponse.of(result));
    }

    @PutMapping("/{reviewUuid}")
    public ApiResponse<ReviewResponse> modifyReview(@PathVariable UUID reviewUuid,
            @RequestBody ModifyReviewRequestDto request) {
        Review result = reviewService.modifyReview(reviewUuid, request.title(), request.content(), request.score());
        return ApiResponse.success(ReviewResponse.of(result));
    }

    @DeleteMapping("/{reviewUuid}")
    public ApiResponse<ReviewResponse> deleteReview(@PathVariable UUID reviewUuid) {
        Review result = reviewService.deleteReview(reviewUuid);
        return ApiResponse.success(ReviewResponse.of(result));
    }

    @GetMapping("/{lawyerId}")
    public ApiResponse<DomainPage<ReviewResponse>> getReviews(@PathVariable Long lawyerId,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String direction, @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {
        DomainPage<Review> result = reviewService.getReviews(lawyerId, sortBy, direction, page, size);

        return ApiResponse.success(ReviewResponse.of(result));
    }

}
