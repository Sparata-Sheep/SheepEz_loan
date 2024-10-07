package com.sheep.ezloan.contact.domain.service;

import com.sheep.ezloan.contact.domain.model.Contract;
import com.sheep.ezloan.contact.domain.model.ContractResult;
import com.sheep.ezloan.contact.domain.repository.ContractRepository;
import com.sheep.ezloan.support.model.DomainPage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ContractService {

    private final ContractRepository contractRepository;

    @Transactional
    public ContractResult createContract(UUID postUuid, Long requestUserId, Long receiveUserId) {

        // 임시 유저네임 생성
        String requestUsername = "temporaryUser1";
        String receiveUsername = "temporaryUser2";

        return contractRepository
            .save(new Contract(postUuid, requestUserId, receiveUserId, requestUsername, receiveUsername));
    }

    @Transactional(readOnly = true)
    public ContractResult getContract(UUID contractUuid) {
        return contractRepository.findByUuid(contractUuid);
    }

    @Transactional(readOnly = true)
    public DomainPage<ContractResult> getAllContracts(int page, int size, String sortBy) {
        return contractRepository.findAll(sortBy, page, size);
    }

    @Transactional(readOnly = true)
    public DomainPage<ContractResult> searchContracts(String username, int page, int size, String sortBy) {
        return contractRepository.searchByUsername(username, sortBy, page, size);
    }

    @Transactional
    public ContractResult acceptContract(UUID contractUuid) {
        return contractRepository.acceptContract(contractUuid);
    }

    @Transactional
    public ContractResult completeContract(UUID contractUuid, Long userId) {

        ContractResult contract = contractRepository.findByUuid(contractUuid);

        if (contract.getRequestUserId().equals(userId))
            return contractRepository.requesterCompleteContract(contractUuid);

        else if (contract.getReceiveUserId().equals(userId))
            return contractRepository.receiverCompleteContract(contractUuid);

        else
            return null;
    }

    @Transactional
    public ContractResult cancelContract(UUID contractUuid, Long userId) {
        ContractResult contract = contractRepository.findByUuid(contractUuid);

        if (contract.getRequestUserId().equals(userId))
            return contractRepository.requesterCancelContract(contractUuid);

        else if (contract.getReceiveUserId().equals(userId))
            return contractRepository.receiverCancelContract(contractUuid);

        else
            return null;
    }

    @Transactional
    public UUID deleteContract(UUID contractUuid) {
        contractRepository.delete(contractUuid);

        return contractUuid;
    }

}
