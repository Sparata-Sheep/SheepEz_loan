package com.sheep.ezloan.lawyer.domain;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.sheep.ezloan.lawyer.domain.model.Review;
import com.sheep.ezloan.lawyer.domain.repository.ReviewRepository;
import com.sheep.ezloan.lawyer.domain.service.ReviewService;
import com.sheep.ezloan.support.model.DomainPage;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class ReviewServiceTest {

    @Mock
    private ReviewRepository reviewRepository;

    @InjectMocks
    private ReviewService reviewService;

    @Test
    public void testCreateReview() {
        // 테스트 데이터 설정
        Long lawyerId = 1L;
        Long reviewerId = 2L;
        String title = "Great Lawyer";
        String content = "Very professional and helpful.";
        Integer score = 5;

        Review review = Review.builder()
            .lawyer(lawyerId)
            .reviewer(reviewerId)
            .title(title)
            .content(content)
            .score(score)
            .build();

        Review savedReview = Review.builder()
            .uuid(UUID.randomUUID())
            .lawyer(lawyerId)
            .reviewer(reviewerId)
            .title(title)
            .content(content)
            .score(score)
            .build();

        // 레포지토리 동작 모킹
        when(reviewRepository.save(any(Review.class))).thenReturn(savedReview);

        // 서비스 메서드 호출
        Review result = reviewService.createReview(lawyerId, reviewerId, title, content, score);

        // 결과 검증
        assertNotNull(result);
        assertEquals(savedReview.getUuid(), result.getUuid());
        assertEquals(lawyerId, result.getLawyer());
        assertEquals(reviewerId, result.getReviewer());
        assertEquals(title, result.getTitle());
        assertEquals(content, result.getContent());
        assertEquals(score, result.getScore());

        // 메서드 호출 검증
        verify(reviewRepository).save(any(Review.class));
    }

    @Test
    public void testModifyReview() {
        // 테스트 데이터 설정
        UUID reviewUuid = UUID.randomUUID();
        String newTitle = "Updated Title";
        String newContent = "Updated Content";
        Integer newScore = 4;

        Review existingReview = Review.builder()
            .uuid(reviewUuid)
            .lawyer(1L)
            .reviewer(2L)
            .title("Old Title")
            .content("Old Content")
            .score(5)
            .build();

        Review modifiedReview = Review.builder()
            .uuid(reviewUuid)
            .lawyer(1L)
            .reviewer(2L)
            .title(newTitle)
            .content(newContent)
            .score(newScore)
            .build();

        // 레포지토리 동작 모킹
        when(reviewRepository.findByUuid(reviewUuid)).thenReturn(existingReview);
        when(reviewRepository.update(any(Review.class))).thenReturn(modifiedReview);

        // 서비스 메서드 호출
        Review result = reviewService.modifyReview(reviewUuid, newTitle, newContent, newScore);

        // 결과 검증
        assertNotNull(result);
        assertEquals(modifiedReview.getUuid(), result.getUuid());
        assertEquals(newTitle, result.getTitle());
        assertEquals(newContent, result.getContent());
        assertEquals(newScore, result.getScore());

        // 메서드 호출 검증
        verify(reviewRepository).findByUuid(reviewUuid);
        verify(reviewRepository).update(any(Review.class));
    }

    @Test
    public void testGetReviews() {
        // 테스트 데이터 설정
        Long lawyerId = 1L;
        String sortBy = "score";
        String direction = "desc";
        Integer page = 0;
        Integer size = 10;

        Review review1 = Review.builder()
            .uuid(UUID.randomUUID())
            .lawyer(lawyerId)
            .reviewer(2L)
            .title("Great Lawyer")
            .content("Very helpful.")
            .score(5)
            .build();

        Review review2 = Review.builder()
            .uuid(UUID.randomUUID())
            .lawyer(lawyerId)
            .reviewer(3L)
            .title("Good Service")
            .content("Satisfied with the service.")
            .score(4)
            .build();

        List<Review> reviewList = Arrays.asList(review1, review2);

        DomainPage<Review> expectedDomainPage = new DomainPage<>(reviewList, 2L, // totalItems
                1, // totalPages
                page, // currentPage
                size, // pageSize
                false // hasNext
        );

        // 레포지토리 동작 모킹
        when(reviewRepository.getReviewsByLawyerId(lawyerId, sortBy, direction, page, size))
            .thenReturn(expectedDomainPage);

        // 서비스 메서드 호출
        DomainPage<Review> result = reviewService.getReviews(lawyerId, sortBy, direction, page, size);

        // 결과 검증
        assertNotNull(result);
        assertEquals(expectedDomainPage.getTotalItems(), result.getTotalItems());
        assertEquals(expectedDomainPage.getTotalPages(), result.getTotalPages());
        assertEquals(expectedDomainPage.getCurrentPage(), result.getCurrentPage());
        assertEquals(expectedDomainPage.getPageSize(), result.getPageSize());
        assertEquals(expectedDomainPage.getHasNext(), result.getHasNext());
        assertEquals(expectedDomainPage.getData(), result.getData());

        // 메서드 호출 검증
        verify(reviewRepository).getReviewsByLawyerId(lawyerId, sortBy, direction, page, size);
    }

    @Test
    public void testDeleteReview() {
        // 테스트 데이터 설정
        UUID reviewUuid = UUID.randomUUID();

        Review existingReview = Review.builder()
            .uuid(reviewUuid)
            .lawyer(1L)
            .reviewer(2L)
            .title("Great Lawyer")
            .content("Very helpful.")
            .score(5)
            .build();

        // 레포지토리 동작 모킹
        when(reviewRepository.findByUuid(reviewUuid)).thenReturn(existingReview);
        when(reviewRepository.delete(existingReview)).thenReturn(existingReview);

        // 서비스 메서드 호출
        Review result = reviewService.deleteReview(reviewUuid);

        // 결과 검증
        assertNotNull(result);
        assertEquals(existingReview, result);

        // 메서드 호출 검증
        verify(reviewRepository).findByUuid(reviewUuid);
        verify(reviewRepository).delete(existingReview);
    }

}
