package com.sheep.ezloan.contact.storage.custom;

import com.sheep.ezloan.contact.storage.entity.ContractEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface ContractRepositoryCustom {

    Page<ContractEntity> searchByUsername(String username, Pageable pageable);

    Page<ContractEntity> findAllForUser(Long userId, Pageable pageable);

}
