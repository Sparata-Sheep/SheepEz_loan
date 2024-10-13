package com.sheep.ezloan.lawyer.storage.entity;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import org.hibernate.annotations.DynamicInsert;

import com.sheep.ezloan.lawyer.domain.model.Review;
import com.sheep.ezloan.support.entity.BaseEntity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "p_reviews")
@DynamicInsert
public class ReviewEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID uuid;

    private Long lawyerId;

    private Long reviewerId;

    private String title;

    private String content;

    private Integer score;

    @Builder
    public ReviewEntity(Long lawyerId, Long reviewerId, String title, String content, Integer score) {
        this.lawyerId = lawyerId;
        this.reviewerId = reviewerId;
        this.title = title;
        this.content = content;
        this.score = score;
    }

    public ReviewEntity update(String title, String content, Integer score) {
        this.title = title;
        this.content = content;
        this.score = score;
        return this;
    }

    public Review toDomain() {
        return Review.builder()
            .uuid(this.uuid)
            .lawyer(this.lawyerId)
            .reviewer(this.reviewerId)
            .title(this.title)
            .content(this.content)
            .score(this.score)
            .build();
    }

}
