package com.sheep.ezloan.lawyer.api.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sheep.ezloan.lawyer.api.dto.CreateLawyerRequestDto;
import com.sheep.ezloan.lawyer.api.dto.LawyerResponse;
import com.sheep.ezloan.lawyer.api.dto.ModifyLawyerRequestDto;
import com.sheep.ezloan.lawyer.domain.model.Lawyer;
import com.sheep.ezloan.lawyer.domain.service.LawyerService;
import com.sheep.ezloan.support.model.DomainPage;
import com.sheep.ezloan.support.response.ApiResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/lawyers")
public class LawyerController {

    public final LawyerService lawyerService;

    @PostMapping
    public ApiResponse<LawyerResponse> createLawyer(@RequestBody CreateLawyerRequestDto requestDto) {
        Lawyer createdLawyer = lawyerService.createLawyer(requestDto.userId(), requestDto.nickname(), requestDto.name(),
                requestDto.email(), requestDto.introduction(), requestDto.certificationInfo(), requestDto.career());

        return ApiResponse.success(LawyerResponse.of(createdLawyer));
    }

    @PutMapping("/{lawyerId}")
    public ApiResponse<LawyerResponse> modifyLawyer(@PathVariable Long lawyerId,
            @RequestBody ModifyLawyerRequestDto requestDto) {
        Lawyer modifiedLawyer = lawyerService.modifyLawyer(lawyerId, requestDto.nickname(), requestDto.email(),
                requestDto.introduction(), requestDto.certificationInfo(), requestDto.career());

        return ApiResponse.success(LawyerResponse.of(modifiedLawyer));
    }

    @GetMapping("/{lawyerId}")
    public ApiResponse<LawyerResponse> getLawyer(@PathVariable Long lawyerId) {
        Lawyer result = lawyerService.getLawyer(lawyerId);
        return ApiResponse.success(LawyerResponse.of(result));
    }

    @DeleteMapping("/{lawyerId}")
    public ApiResponse<LawyerResponse> deleteLawyer(@PathVariable Long lawyerId) {
        Lawyer result = lawyerService.deleteLawyer(lawyerId);
        return ApiResponse.success(LawyerResponse.of(result));
    }

    @GetMapping
    public ApiResponse<DomainPage<LawyerResponse>> searchLawyers(@RequestParam(defaultValue = "") String searchQuery,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String direction, @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {
        DomainPage<Lawyer> result = lawyerService.getLawyersBySearch(searchQuery, sortBy, direction, page, size);

        return ApiResponse
            .success(DomainPage.of(result.getData().stream().map(LawyerResponse::of).toList(), result.getTotalItems(),
                    result.getTotalPages(), result.getCurrentPage(), result.getPageSize(), result.getHasNext()));
    }

}
