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
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sheep.ezloan.lawyer.storage.entity.LawyerEntity;
import com.sheep.ezloan.lawyer.storage.entity.QLawyerEntity;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class LawyerJpaRepositoryImpl implements LawyerCustomRepository {

    private final JPAQueryFactory queryFactory;

    QLawyerEntity lawyer = QLawyerEntity.lawyerEntity;

    @Override
    public Page<LawyerEntity> searchLawyers(String searchQuery, Pageable pageable, Boolean isAccepted) {
        List<LawyerEntity> contents = queryFactory.select(lawyer)
            .from(lawyer)
            .where(searchQueryEq(searchQuery))
            .where(lawyer.isAccepted.eq(isAccepted))
            .where(lawyer.isDeleted.isFalse())
            .orderBy(getOrderSpecifiers(pageable.getSort()).toArray(OrderSpecifier[]::new))
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();

        JPAQuery<Long> count = queryFactory.select(lawyer.count()).from(lawyer).where(searchQueryEq(searchQuery));

        return PageableExecutionUtils.getPage(contents, pageable, count::fetchOne);
    }

    private BooleanExpression searchQueryEq(String searchQuery) {
        return searchQuery.isEmpty() ? null : lawyer.name.contains(searchQuery);
    }

    private List<OrderSpecifier> getOrderSpecifiers(Sort sort) {
        List<OrderSpecifier> orderSpecifiers = new ArrayList<>();
        sort.stream().forEach(order -> {
            Order direction = order.isAscending() ? Order.ASC : Order.DESC;
            String property = order.getProperty();
            Path<Object> target = Expressions.path(Object.class, QLawyerEntity.lawyerEntity, property);
            orderSpecifiers.add(new OrderSpecifier(direction, target));
        });
        return orderSpecifiers;
    }

}
