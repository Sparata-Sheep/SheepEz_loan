package com.sheep.ezloan.contact.api.controller;

import com.sheep.ezloan.contact.api.controller.dto.ContractDto;
import com.sheep.ezloan.contact.domain.model.ContractResult;
import com.sheep.ezloan.contact.domain.service.ContractService;
import com.sheep.ezloan.support.error.CoreApiException;
import com.sheep.ezloan.support.model.DomainPage;
import com.sheep.ezloan.support.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/contracts")
public class ContractController {

    private final ContractService contractService;

    @PostMapping
    public ApiResponse<?> createContract(@Valid @RequestBody ContractDto.Request contractDto) {
        ContractResult contract;

        try {
            contract = contractService.createContract(contractDto.getPostUuid(), contractDto.getRequestUserId(),
                    contractDto.getReceiveUserId());
        }
        catch (CoreApiException e) {
            return ApiResponse.error(e.getErrorType());
        }

        return ApiResponse.success(ContractDto.Response.of(contract));
    }

    @GetMapping("/{contractUuid}")
    public ApiResponse<?> getContract(@PathVariable(value = "contractUuid") UUID contractUuid) {
        ContractResult contract;

        try {
            contract = contractService.getContract(contractUuid);
        }
        catch (CoreApiException e) {
            return ApiResponse.error(e.getErrorType());
        }

        return ApiResponse.success(ContractDto.Response.of(contract));
    }

    @GetMapping
    public ApiResponse<?> getAllContracts(@RequestParam("page") int page, @RequestParam("size") int size,
            @RequestParam("sort") String sort) {
        DomainPage<ContractResult> contracts;

        try {
            contracts = contractService.getAllContracts(page, size, sort);
        }
        catch (CoreApiException e) {
            return ApiResponse.error(e.getErrorType());
        }

        return ApiResponse.success(ContractDto.Response.of(contracts));
    }

    @GetMapping("/search/{username}")
    public ApiResponse<?> searchContracts(@PathVariable(value = "username") String username,
            @RequestParam("page") int page, @RequestParam("size") int size, @RequestParam("sort") String sort) {
        DomainPage<ContractResult> contracts;

        try {
            contracts = contractService.searchContracts(username, page, size, sort);
        }
        catch (CoreApiException e) {
            return ApiResponse.error(e.getErrorType());
        }
        return ApiResponse.success(ContractDto.Response.of(contracts));
    }

    @PatchMapping("/accept/{contractUuid}")
    public ApiResponse<?> acceptContract(@PathVariable(value = "contractUuid") UUID contractUuid) {
        ContractResult contract;

        try {
            contract = contractService.acceptContract(contractUuid);
        }
        catch (CoreApiException e) {
            return ApiResponse.error(e.getErrorType());
        }

        return ApiResponse.success(ContractDto.Response.of(contract));
    }

    @PatchMapping("/complete/{contractUuid}")
    public ApiResponse<?> completeContract(@RequestHeader("User-Id") Long userId,
            @PathVariable(value = "contractUuid") UUID contractUuid) {
        ContractResult contract;

        try {
            contract = contractService.completeContract(contractUuid, userId);
        }
        catch (CoreApiException e) {
            return ApiResponse.error(e.getErrorType());
        }

        return ApiResponse.success(ContractDto.Response.of(contract));
    }

    @PatchMapping("/cancel/{contractUuid}")
    public ApiResponse<?> cancelContract(@RequestHeader("User-Id") Long userId,
            @PathVariable(value = "contractUuid") UUID contractUuid) {
        ContractResult contract;

        try {
            contract = contractService.cancelContract(contractUuid, userId);
        }
        catch (CoreApiException e) {
            return ApiResponse.error(e.getErrorType());
        }

        return ApiResponse.success(ContractDto.Response.of(contract));
    }

    @DeleteMapping("/{contractUuid}")
    public ApiResponse<?> deleteContract(@PathVariable(value = "contractUuid") UUID contractUuid) {
        ContractDto.DeleteResponse deletedContract;

        try {
            deletedContract = ContractDto.DeleteResponse.of(contractService.deleteContract(contractUuid));
        }
        catch (CoreApiException e) {
            return ApiResponse.error(e.getErrorType());
        }

        return ApiResponse.success(deletedContract);
    }

}
