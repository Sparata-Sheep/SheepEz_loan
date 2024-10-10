package com.sheep.ezloan.contact.domain.repository;

import com.sheep.ezloan.contact.domain.model.Contract;
import com.sheep.ezloan.contact.domain.model.ContractResult;
import com.sheep.ezloan.support.model.DomainPage;

import java.util.UUID;

public interface ContractRepository {

    ContractResult save(Contract contract);

    ContractResult findByUuid(UUID contractUuid);

    DomainPage<ContractResult> findAll(String sortBy, int page, int size);

    DomainPage<ContractResult> searchByUsername(String username, String sortBy, int page, int size);

    ContractResult acceptContract(UUID contractUuid);

    ContractResult requesterCompleteContract(UUID contractUuid);

    ContractResult receiverCompleteContract(UUID contractUuid);

    ContractResult requesterCancelContract(UUID contractUuid);

    ContractResult receiverCancelContract(UUID contractUuid);

    UUID delete(UUID contractUuid);

}
