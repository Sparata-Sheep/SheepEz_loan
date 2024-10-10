package com.sheep.ezloan.contact.domain.service;

import com.sheep.ezloan.contact.domain.model.*;
import com.sheep.ezloan.contact.domain.repository.ContractRepository;
import com.sheep.ezloan.contact.domain.repository.PostRepository;
import com.sheep.ezloan.support.error.CoreApiException;
import com.sheep.ezloan.support.error.ErrorType;
import com.sheep.ezloan.support.model.DomainPage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ContractService {

    private final ContractRepository contractRepository;

    private final PostRepository postRepository;

    @Transactional
    public ContractResult createContract(UUID postUuid, Long requestUserId, Long receiveUserId) {

        if (postRepository.findByUuid(postUuid) == null) {
            throw new CoreApiException(ErrorType.NOT_FOUND_ERROR);
        }

        // 임시 유저네임 생성
        String requestUsername = "temporaryUser1";
        String receiveUsername = "temporaryUser2";

        return contractRepository
            .save(new Contract(postUuid, requestUserId, receiveUserId, requestUsername, receiveUsername));
    }

    @Transactional(readOnly = true)
    public ContractResult getContract(UUID contractUuid) {
        ContractResult result = contractRepository.findByUuid(contractUuid);

        if (result == null) {
            throw new CoreApiException(ErrorType.NOT_FOUND_ERROR);
        }
        return result;
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
        ContractResult result = contractRepository.acceptContract(contractUuid);

        if (result == null) {
            throw new CoreApiException(ErrorType.NOT_FOUND_ERROR);
        }

        postRepository.updateStatus(result.getPostUuid(), PostStatus.IN_PROGRESS);

        return result;
    }

    @Transactional
    public ContractResult completeContract(UUID contractUuid, Long userId) {

        ContractResult contract = contractRepository.findByUuid(contractUuid);

        if (contract == null) {
            throw new CoreApiException(ErrorType.NOT_FOUND_ERROR);
        }

        if (contract.getRequestUserId().equals(userId))
            contract = contractRepository.requesterCompleteContract(contractUuid);

        else if (contract.getReceiveUserId().equals(userId))
            contract = contractRepository.receiverCompleteContract(contractUuid);

        else
            throw new CoreApiException(ErrorType.FORBIDDEN_ERROR);

        if (contract.getStatus() == ContractStatus.COMPLETED) {
            postRepository.updateStatus(contract.getPostUuid(), PostStatus.COMPLETED);
        }

        return contract;
    }

    @Transactional
    public ContractResult cancelContract(UUID contractUuid, Long userId) {
        ContractResult contract = contractRepository.findByUuid(contractUuid);

        if (contract == null) {
            throw new CoreApiException(ErrorType.NOT_FOUND_ERROR);
        }

        if (contract.getRequestUserId().equals(userId))
            contract = contractRepository.requesterCancelContract(contractUuid);

        else if (contract.getReceiveUserId().equals(userId))
            contract = contractRepository.receiverCancelContract(contractUuid);

        else
            throw new CoreApiException(ErrorType.FORBIDDEN_ERROR);

        if (contract.getStatus() == ContractStatus.CANCELED) {
            postRepository.updateStatus(contract.getPostUuid(), PostStatus.OPEN);
        }

        return contract;
    }

    @Transactional
    public UUID deleteContract(UUID contractUuid) {
        UUID resultUuid = contractRepository.delete(contractUuid);

        if (resultUuid == null)
            throw new CoreApiException(ErrorType.NOT_FOUND_ERROR);

        return resultUuid;
    }

}
