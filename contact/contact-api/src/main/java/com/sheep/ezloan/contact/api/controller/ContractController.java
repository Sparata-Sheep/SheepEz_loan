package com.sheep.ezloan.contact.api.controller;

import com.sheep.ezloan.contact.api.controller.dto.ContractDto;
import com.sheep.ezloan.contact.domain.model.ContractResult;
import com.sheep.ezloan.contact.domain.service.ContractService;
import com.sheep.ezloan.support.error.CoreApiException;
import com.sheep.ezloan.support.error.ErrorType;
import com.sheep.ezloan.support.model.DomainPage;
import com.sheep.ezloan.support.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/contracts")
public class ContractController {

    private final ContractService contractService;

    @PostMapping
    public ApiResponse<?> createContract(@RequestHeader("X-Username") String username,
            @Valid @RequestBody ContractDto.Request contractDto) {
        ContractResult contract;

        try {
            contract = contractService.createContract(username, contractDto.getPostUuid(),
                    contractDto.getRequestUserId(), contractDto.getReceiveUserId(), contractDto.getReceiveUsername());
        }
        catch (CoreApiException e) {
            return ApiResponse.error(e.getErrorType());
        }

        return ApiResponse.success(ContractDto.Response.of(contract));
    }

    @GetMapping("/{contractUuid}")
    public ApiResponse<?> getContract(@RequestHeader("X-User-Id") Long userId, @RequestHeader("X-Role") String role,
            @PathVariable(value = "contractUuid") UUID contractUuid) {
        ContractResult contract;

        try {
            contract = contractService.getContract(userId, role, contractUuid);
        }
        catch (CoreApiException e) {
            return ApiResponse.error(e.getErrorType());
        }

        return ApiResponse.success(ContractDto.Response.of(contract));
    }

    @GetMapping
    public ApiResponse<?> getAllContracts(@RequestHeader("X-User-Id") Long userId, @RequestHeader("X-Role") String role,
            @RequestParam("page") int page, @RequestParam("size") int size, @RequestParam("sort") String sort) {
        DomainPage<ContractResult> contracts;

        try {
            contracts = contractService.getAllContracts(userId, role, page, size, sort);
        }
        catch (CoreApiException e) {
            return ApiResponse.error(e.getErrorType());
        }

        return ApiResponse.success(ContractDto.Response.of(contracts));
    }

    @GetMapping("/search/{username}")
    public ApiResponse<?> searchContracts(@RequestHeader("X-Role") String role,
            @PathVariable(value = "username") String username, @RequestParam("page") int page,
            @RequestParam("size") int size, @RequestParam("sort") String sort) {
        DomainPage<ContractResult> contracts;

        try {
            if (!Objects.equals(role, "MASTER"))
                throw new CoreApiException(ErrorType.FORBIDDEN_ERROR);
            contracts = contractService.searchContracts(username, page, size, sort);
        }
        catch (CoreApiException e) {
            return ApiResponse.error(e.getErrorType());
        }
        return ApiResponse.success(ContractDto.Response.of(contracts));
    }

    @PatchMapping("/accept/{contractUuid}")
    public ApiResponse<?> acceptContract(@RequestHeader("X-User-Id") Long userId, @RequestHeader("X-Role") String role,
            @PathVariable(value = "contractUuid") UUID contractUuid) {
        ContractResult contract;

        try {
            contract = contractService.acceptContract(userId, role, contractUuid);
        }
        catch (CoreApiException e) {
            return ApiResponse.error(e.getErrorType());
        }

        return ApiResponse.success(ContractDto.Response.of(contract));
    }

    @PatchMapping("/complete/{contractUuid}")
    public ApiResponse<?> completeContract(@RequestHeader("X-User-Id") Long userId,
            @RequestHeader("X-Role") String role, @PathVariable(value = "contractUuid") UUID contractUuid) {
        ContractResult contract;

        try {
            contract = contractService.completeContract(userId, role, contractUuid);
        }
        catch (CoreApiException e) {
            return ApiResponse.error(e.getErrorType());
        }

        return ApiResponse.success(ContractDto.Response.of(contract));
    }

    @PatchMapping("/cancel/{contractUuid}")
    public ApiResponse<?> cancelContract(@RequestHeader("X-User-Id") Long userId, @RequestHeader("X-Role") String role,
            @PathVariable(value = "contractUuid") UUID contractUuid) {
        ContractResult contract;

        try {
            contract = contractService.cancelContract(userId, role, contractUuid);
        }
        catch (CoreApiException e) {
            return ApiResponse.error(e.getErrorType());
        }

        return ApiResponse.success(ContractDto.Response.of(contract));
    }

    @DeleteMapping("/{contractUuid}")
    public ApiResponse<?> deleteContract(@RequestHeader("X-Role") String role,
            @PathVariable(value = "contractUuid") UUID contractUuid) {
        ContractDto.DeleteResponse deletedContract;

        try {
            if (!Objects.equals(role, "MASTER"))
                throw new CoreApiException(ErrorType.FORBIDDEN_ERROR);
            deletedContract = ContractDto.DeleteResponse.of(contractService.deleteContract(contractUuid));
        }
        catch (CoreApiException e) {
            return ApiResponse.error(e.getErrorType());
        }

        return ApiResponse.success(deletedContract);
    }

}
