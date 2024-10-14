package com.sheep.ezloan.contact.storage.implement;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sheep.ezloan.contact.domain.model.ContractResult;
import com.sheep.ezloan.contact.storage.custom.ContractRepositoryCustom;
import com.sheep.ezloan.contact.storage.entity.ContractEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

import static com.sheep.ezloan.contact.storage.entity.QContractEntity.contractEntity;

@Component
@RequiredArgsConstructor
public class ContractRepositoryImpl implements ContractRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<ContractEntity> searchByUsername(String username, Pageable pageable) {
        JPAQuery<ContractEntity> query = queryFactory.selectFrom(contractEntity)
            .where((contractEntity.requestUsername.eq(username).or(contractEntity.receiveUsername.eq(username)))
                .and(contractEntity.isDeleted.eq(false)));

        List<ContractEntity> results = query.fetch();

        return new PageImpl<>(results, pageable, results.size());
    }

    @Override
    public Page<ContractEntity> findAllForUser(Long userId, Pageable pageable) {
        JPAQuery<ContractEntity> query = queryFactory.selectFrom(contractEntity)
            .where((contractEntity.requestUserId.eq(userId).or(contractEntity.receiveUserId.eq(userId)))
                .and(contractEntity.isDeleted.eq(false)));

        List<ContractEntity> results = query.fetch();

        return new PageImpl<>(results, pageable, results.size());
    }

}
