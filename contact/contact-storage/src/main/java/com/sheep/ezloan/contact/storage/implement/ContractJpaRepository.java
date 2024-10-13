package com.sheep.ezloan.contact.storage.implement;

import com.sheep.ezloan.contact.storage.entity.ContractEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ContractJpaRepository extends JpaRepository<ContractEntity, UUID> {

    Optional<ContractEntity> findByContractUuidAndIsDeletedFalse(UUID contractUuid);

    Page<ContractEntity> findAllByIsDeletedFalse(Pageable pageable);

}
