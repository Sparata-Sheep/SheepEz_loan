package com.sheep.ezloan.contact.storage.implement;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sheep.ezloan.contact.storage.custom.PostRepositoryCustom;
import com.sheep.ezloan.contact.storage.entity.PostEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.sheep.ezloan.contact.storage.entity.QPostEntity.postEntity;

@Component
@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<PostEntity> searchByKeyword(String keyword, Pageable pageable) {
        JPAQuery<PostEntity> query = queryFactory.selectFrom(postEntity)
            .where((postEntity.title.contains(keyword).or(postEntity.content.contains(keyword)))
                .and(postEntity.isDeleted.eq(false)));

        List<PostEntity> results = query.fetch();

        return new PageImpl<>(results, pageable, results.size());
    }

}
