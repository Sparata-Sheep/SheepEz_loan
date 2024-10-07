package com.sheep.ezloan.contact.api.controller;

import com.sheep.ezloan.contact.api.controller.dto.ContractDto;
import com.sheep.ezloan.contact.domain.model.ContractResult;
import com.sheep.ezloan.contact.domain.service.ContractService;
import com.sheep.ezloan.support.error.CoreApiException;
import com.sheep.ezloan.support.error.ErrorType;
import com.sheep.ezloan.support.model.DomainPage;
import com.sheep.ezloan.support.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/contracts")
public class ContractController {

    private final ContractService contractService;

    @PostMapping
    public ApiResponse<ContractDto.Response> createContract(@RequestBody ContractDto.Request contractDto) {
        ContractResult contract = contractService.createContract(contractDto.getPostUuid(),
                contractDto.getRequestUserId(), contractDto.getReceiveUserId());

        return ApiResponse.success(ContractDto.Response.of(contract));
    }

    @GetMapping("/{contractUuid}")
    public ApiResponse<ContractDto.Response> getContract(@PathVariable(value = "contractUuid") UUID contractUuid) {
        ContractResult contract = contractService.getContract(contractUuid);

        return ApiResponse.success(ContractDto.Response.of(contract));
    }

    @GetMapping
    public ApiResponse<List<ContractDto.Response>> getAllContracts(@RequestParam("page") int page,
            @RequestParam("size") int size, @RequestParam("sort") String sort) {
        DomainPage<ContractResult> contracts = contractService.getAllContracts(page, size, sort);

        return ApiResponse.success(ContractDto.Response.of(contracts));
    }

    @GetMapping("/search/{username}")
    public ApiResponse<List<ContractDto.Response>> searchContracts(@PathVariable(value = "username") String username,
            @RequestParam("page") int page, @RequestParam("size") int size, @RequestParam("sort") String sort) {
        DomainPage<ContractResult> contracts = contractService.searchContracts(username, page, size, sort);

        return ApiResponse.success(ContractDto.Response.of(contracts));
    }

    @PatchMapping("/accept/{contractUuid}")
    public ApiResponse<ContractDto.Response> acceptContract(@PathVariable(value = "contractUuid") UUID contractUuid) {
        ContractResult contract = contractService.acceptContract(contractUuid);

        return ApiResponse.success(ContractDto.Response.of(contract));
    }

    @PatchMapping("/complete/{contractUuid}")
    public ApiResponse<ContractDto.Response> completeContract(@RequestHeader("User-Id") Long userId,
            @PathVariable(value = "contractUuid") UUID contractUuid) {
        ContractResult contract = contractService.completeContract(contractUuid, userId);

        if (contract == null)
            throw new CoreApiException(ErrorType.FORBIDDEN, null);
        return ApiResponse.success(ContractDto.Response.of(contract));
    }

    @PatchMapping("/cancel/{contractUuid}")
    public ApiResponse<ContractDto.Response> cancelContract(@RequestHeader("User-Id") Long userId,
            @PathVariable(value = "contractUuid") UUID contractUuid) {
        ContractResult contract = contractService.cancelContract(contractUuid, userId);

        if (contract == null)
            throw new CoreApiException(ErrorType.FORBIDDEN, null);
        return ApiResponse.success(ContractDto.Response.of(contract));
    }

    @DeleteMapping("/{contractUuid}")
    public ApiResponse<ContractDto.DeleteResponse> deleteContract(
            @PathVariable(value = "contractUuid") UUID contractUuid) {
        ContractDto.DeleteResponse deletedContract = ContractDto.DeleteResponse
            .of(contractService.deleteContract(contractUuid));

        return ApiResponse.success(deletedContract);
    }

}
