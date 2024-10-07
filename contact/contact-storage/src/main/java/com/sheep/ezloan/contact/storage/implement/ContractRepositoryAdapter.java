package com.sheep.ezloan.contact.storage.implement;

import com.sheep.ezloan.contact.domain.model.Contract;
import com.sheep.ezloan.contact.domain.model.ContractResult;
import com.sheep.ezloan.contact.domain.repository.ContractRepository;
import com.sheep.ezloan.contact.storage.custom.ContractRepositoryCustom;
import com.sheep.ezloan.contact.storage.entity.ContractEntity;
import com.sheep.ezloan.support.model.DomainPage;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class ContractRepositoryAdapter implements ContractRepository {

    private final ContractJpaRepository contractJpaRepository;

    private final ContractRepositoryCustom contractRepositoryCustom;

    @Override
    public ContractResult save(Contract contract) {
        ContractEntity contractEntity = new ContractEntity(contract.getPostUuid(), contract.getRequestUserId(),
                contract.getReceiveUserId(), contract.getRequestUsername(), contract.getReceiveUsername());
        return contractJpaRepository.save(contractEntity).toDomain();

    }

    @Override
    public ContractResult findByUuid(UUID contractUuid) {
        return contractJpaRepository.findById(contractUuid).orElseThrow(EntityNotFoundException::new).toDomain();
    }

    @Override
    public DomainPage<ContractResult> findAll(String sortBy, int page, int size) {
        Sort.Direction direction = Sort.Direction.ASC;
        Sort sort = Sort.by(direction, sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<ContractResult> contractResultPage = contractJpaRepository.findAll(pageable).map(ContractEntity::toDomain);

        return DomainPage.of(contractResultPage.getContent(), contractResultPage.getTotalElements(),
                contractResultPage.getTotalPages(), contractResultPage.getNumber(), contractResultPage.getSize(),
                contractResultPage.hasNext());
    }

    @Override
    public DomainPage<ContractResult> searchByUsername(String username, String sortBy, int page, int size) {
        Sort.Direction direction = Sort.Direction.ASC;
        Sort sort = Sort.by(direction, sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<ContractResult> contractResultPage = contractRepositoryCustom.searchByUsername(username, pageable)
            .map(ContractEntity::toDomain);

        return DomainPage.of(contractResultPage.getContent(), contractResultPage.getTotalElements(),
                contractResultPage.getTotalPages(), contractResultPage.getNumber(), contractResultPage.getSize(),
                contractResultPage.hasNext());
    }

    @Override
    public ContractResult acceptContract(UUID contractUuid) {
        ContractEntity entity = contractJpaRepository.findById(contractUuid).orElseThrow(EntityNotFoundException::new);

        entity.accept();
        return contractJpaRepository.save(entity).toDomain();
    }

    @Override
    public ContractResult requesterCompleteContract(UUID contractUuid) {
        ContractEntity entity = contractJpaRepository.findById(contractUuid).orElseThrow(EntityNotFoundException::new);

        entity.requesterComplete();
        return contractJpaRepository.save(entity).toDomain();
    }

    @Override
    public ContractResult receiverCompleteContract(UUID contractUuid) {
        ContractEntity entity = contractJpaRepository.findById(contractUuid).orElseThrow(EntityNotFoundException::new);

        entity.receiverComplete();
        return contractJpaRepository.save(entity).toDomain();
    }

    @Override
    public ContractResult requesterCancelContract(UUID contractUuid) {
        ContractEntity entity = contractJpaRepository.findById(contractUuid).orElseThrow(EntityNotFoundException::new);

        entity.requesterCancel();
        return contractJpaRepository.save(entity).toDomain();
    }

    @Override
    public ContractResult receiverCancelContract(UUID contractUuid) {
        ContractEntity entity = contractJpaRepository.findById(contractUuid).orElseThrow(EntityNotFoundException::new);

        entity.receiverCancel();
        return contractJpaRepository.save(entity).toDomain();
    }

    @Override
    public void delete(UUID contractUuid) {
        contractJpaRepository.deleteById(contractUuid);
    }

}
