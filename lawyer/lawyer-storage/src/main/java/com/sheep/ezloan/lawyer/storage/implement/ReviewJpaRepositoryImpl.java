package com.sheep.ezloan.lawyer.storage.implement;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sheep.ezloan.lawyer.storage.entity.QLawyerEntity;
import com.sheep.ezloan.lawyer.storage.entity.QReviewEntity;
import com.sheep.ezloan.lawyer.storage.entity.ReviewEntity;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ReviewJpaRepositoryImpl implements ReviewCustomRepository {

    private final JPAQueryFactory queryFactory;

    QReviewEntity review = QReviewEntity.reviewEntity;

    @Override
    public Page<ReviewEntity> getReviewsBySorting(Long lawyerId, Pageable pageable) {
        List<ReviewEntity> contents = queryFactory.select(review)
            .from(review)
            .where(review.lawyerId.eq(lawyerId))
            .where(review.isDeleted.isFalse())
            .orderBy(getOrderSpecifiers(pageable.getSort()).toArray(OrderSpecifier[]::new))
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();

        JPAQuery<Long> count = queryFactory.select(review.count()).from(review).where(review.lawyerId.eq(lawyerId));

        return PageableExecutionUtils.getPage(contents, pageable, count::fetchOne);
    }

    private List<OrderSpecifier> getOrderSpecifiers(Sort sort) {
        List<OrderSpecifier> orderSpecifiers = new ArrayList<>();
        sort.stream().forEach(order -> {
            Order direction = order.isAscending() ? Order.ASC : Order.DESC;
            String property = order.getProperty();
            Path<Object> target = Expressions.path(Object.class, review, property);
            orderSpecifiers.add(new OrderSpecifier(direction, target));
        });
        return orderSpecifiers;
    }

}
