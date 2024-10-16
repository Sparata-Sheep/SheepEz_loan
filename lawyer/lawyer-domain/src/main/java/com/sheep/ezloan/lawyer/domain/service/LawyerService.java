package com.sheep.ezloan.lawyer.domain.service;

import org.springframework.stereotype.Service;

import com.sheep.ezloan.lawyer.domain.model.Lawyer;
import com.sheep.ezloan.lawyer.domain.repository.LawyerRepository;
import com.sheep.ezloan.support.model.DomainPage;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LawyerService {

    private final LawyerRepository lawyerRepository;

    public Lawyer createLawyer(Long userId, String nickname, String name, String email, String introduction,
            String certificationInfo, String career) {
        Lawyer lawyer = Lawyer.builder()
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
        return lawyerRepository.saveLawyer(lawyer);
    }

    public Lawyer modifyLawyer(Long id, String nickname, String email, String introduction, String certificationInfo,
            String career) {
        Lawyer targetLawyer = lawyerRepository.findById(id);
        Lawyer modifiedLawyer = targetLawyer.modify(nickname, email, introduction, certificationInfo, career);

        return lawyerRepository.update(modifiedLawyer);
    }

    public Lawyer getLawyer(Long id) {
        return lawyerRepository.findById(id);
    }

    public Lawyer deleteLawyer(Long id) {
        return lawyerRepository.deleteById(id);
    }

    public DomainPage<Lawyer> getLawyersBySearch(String searchQuery, String sortBy, String direction, Integer page,
            Integer size) {
        return lawyerRepository.searchLawyers(searchQuery, sortBy, direction, page, size, true);
    }

    public DomainPage<Lawyer> getWaitingLawyersBySearch(String searchQuery, String sortBy, String direction,
            Integer page, Integer size) {
        return lawyerRepository.searchLawyers(searchQuery, sortBy, direction, page, size, false);
    }

    public Lawyer approveLawyer(Long id) {
        Lawyer lawyer = lawyerRepository.findById(id);
        if (lawyer.getIsAccepted() == true) {
            throw new RuntimeException("Lawyer is already accepted");
        }
        lawyer.accept();
        return lawyerRepository.updateIsAccepted(lawyer);
    }

}
