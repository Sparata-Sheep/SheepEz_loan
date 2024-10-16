package com.sheep.ezloan.lawyer.domain;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;

import com.sheep.ezloan.lawyer.domain.model.Lawyer;
import com.sheep.ezloan.lawyer.domain.repository.LawyerRepository;
import com.sheep.ezloan.lawyer.domain.service.LawyerService;
import com.sheep.ezloan.support.model.DomainPage;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class LawyerServiceTest {

    @Mock
    private LawyerRepository lawyerRepository;

    @InjectMocks
    private LawyerService lawyerService;

    @Test
    public void testCreateLawyer() {
        // 테스트 데이터 설정
        Long userId = 1L;
        String nickname = "john_doe";
        String name = "John Doe";
        String email = "john@example.com";
        String introduction = "Experienced lawyer";
        String certificationInfo = "Certified in XYZ law";
        String career = "10 years of experience";

        // 기대되는 Lawyer 객체 생성
        Lawyer expectedLawyer = Lawyer.builder()
            .id(userId)
            .nickname(nickname)
            .name(name)
            .email(email)
            .introduction(introduction)
            .certificationInfo(certificationInfo)
            .career(career)
            .isAccepted(false)
            .isPublic(false)
            .build();

        // 레포지토리의 동작을 모킹
        when(lawyerRepository.saveLawyer(any(Lawyer.class))).thenReturn(expectedLawyer);

        // 서비스 메서드 호출
        Lawyer result = lawyerService.createLawyer(userId, nickname, name, email, introduction, certificationInfo,
                career);

        // 결과 검증
        assertEquals(expectedLawyer, result);
        verify(lawyerRepository).saveLawyer(any(Lawyer.class));
    }

    @Test
    public void testModifyLawyer() {
        // 테스트 입력 데이터 설정
        Long id = 1L;
        String newNickname = "new_nickname";
        String newEmail = "new.email@example.com";
        String newIntroduction = "Updated introduction";
        String newCertificationInfo = "Updated certification";
        String newCareer = "15 years of experience";

        // 기존 Lawyer 객체 생성
        Lawyer existingLawyer = Lawyer.builder()
            .id(id)
            .nickname("old_nickname")
            .name("John Doe")
            .email("old.email@example.com")
            .introduction("Old introduction")
            .certificationInfo("Old certification")
            .career("10 years of experience")
            .isAccepted(true)
            .isPublic(true)
            .build();

        // 수정된 Lawyer 객체 생성
        Lawyer modifiedLawyer = Lawyer.builder()
            .id(id)
            .nickname(newNickname)
            .name("John Doe") // 이름은 변경되지 않음
            .email(newEmail)
            .introduction(newIntroduction)
            .certificationInfo(newCertificationInfo)
            .career(newCareer)
            .isAccepted(true)
            .isPublic(true)
            .build();

        // 레포지토리 동작 모킹
        when(lawyerRepository.findById(id)).thenReturn(existingLawyer);
        when(lawyerRepository.update(any(Lawyer.class))).thenReturn(modifiedLawyer);

        // 서비스 메서드 호출
        Lawyer result = lawyerService.modifyLawyer(id, newNickname, newEmail, newIntroduction, newCertificationInfo,
                newCareer);

        // 결과 검증
        assertNotNull(result);
        assertEquals(modifiedLawyer, result);
        assertEquals(newNickname, result.getNickname());
        assertEquals(newEmail, result.getEmail());
        assertEquals(newIntroduction, result.getIntroduction());
        assertEquals(newCertificationInfo, result.getCertificationInfo());
        assertEquals(newCareer, result.getCareer());

        // 메서드 호출 검증
        verify(lawyerRepository).findById(id);
        verify(lawyerRepository).update(any(Lawyer.class));
    }

    @Test
    public void testGetLawyer() {
        // 테스트 데이터 설정
        Long id = 1L;
        Lawyer expectedLawyer = Lawyer.builder()
            .id(id)
            .nickname("john_doe")
            .name("John Doe")
            .email("john.doe@example.com")
            .introduction("Experienced lawyer")
            .certificationInfo("Certified in XYZ law")
            .career("10 years of experience")
            .isAccepted(true)
            .isPublic(true)
            .build();

        // 레포지토리 동작 모킹
        when(lawyerRepository.findById(id)).thenReturn(expectedLawyer);

        // 서비스 메서드 호출
        Lawyer result = lawyerService.getLawyer(id);

        // 결과 검증
        assertNotNull(result);
        assertEquals(expectedLawyer, result);

        // 메서드 호출 검증
        verify(lawyerRepository).findById(id);
    }

    @Test
    public void testDeleteLawyer_Success() {
        // 테스트 데이터 설정
        Long id = 1L;
        Lawyer existingLawyer = Lawyer.builder()
            .id(id)
            .nickname("john_doe")
            .name("John Doe")
            .email("john.doe@example.com")
            .introduction("Experienced lawyer")
            .certificationInfo("Certified in XYZ law")
            .career("10 years of experience")
            .isAccepted(true)
            .isPublic(true)
            .build();

        // 레포지토리 동작 모킹
        when(lawyerRepository.deleteById(id)).thenReturn(existingLawyer);

        // 서비스 메서드 호출
        Lawyer result = lawyerService.deleteLawyer(id);

        // 결과 검증
        assertNotNull(result);
        assertEquals(existingLawyer, result);

        // 메서드 호출 검증
        verify(lawyerRepository).deleteById(id);
    }

    @Test
    public void testGetLawyersBySearch() {
        // 테스트 데이터 설정
        String searchQuery = "John";
        String sortBy = "name";
        String direction = "asc";
        Integer page = 0;
        Integer size = 10;

        Lawyer lawyer1 = Lawyer.builder()
            .id(1L)
            .name("John Doe")
            .nickname("johndoe")
            .email("john.doe@example.com")
            .build();

        Lawyer lawyer2 = Lawyer.builder()
            .id(2L)
            .name("Johnny Smith")
            .nickname("johnnysmith")
            .email("johnny.smith@example.com")
            .build();

        List<Lawyer> lawyerList = Arrays.asList(lawyer1, lawyer2);

        DomainPage<Lawyer> expectedDomainPage = new DomainPage<>(lawyerList, 2L, // totalItems
                1, // totalPages
                page, // currentPage
                size, // pageSize
                false // hasNext
        );

        // 레포지토리 동작 모킹
        when(lawyerRepository.searchLawyers(searchQuery, sortBy, direction, page, size, true))
            .thenReturn(expectedDomainPage);

        // 서비스 메서드 호출
        DomainPage<Lawyer> result = lawyerService.getLawyersBySearch(searchQuery, sortBy, direction, page, size);

        // 결과 검증
        assertNotNull(result);
        assertEquals(expectedDomainPage.getTotalItems(), result.getTotalItems());
        assertEquals(expectedDomainPage.getTotalPages(), result.getTotalPages());
        assertEquals(expectedDomainPage.getCurrentPage(), result.getCurrentPage());
        assertEquals(expectedDomainPage.getPageSize(), result.getPageSize());
        assertEquals(expectedDomainPage.getHasNext(), result.getHasNext());
        assertEquals(expectedDomainPage.getData(), result.getData());

        // 메서드 호출 검증
        verify(lawyerRepository).searchLawyers(searchQuery, sortBy, direction, page, size, true);
    }

}
