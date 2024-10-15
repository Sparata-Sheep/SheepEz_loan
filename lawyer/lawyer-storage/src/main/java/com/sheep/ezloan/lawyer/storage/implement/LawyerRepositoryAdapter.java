package com.sheep.ezloan.lawyer.storage.implement;

import jakarta.persistence.EntityNotFoundException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sheep.ezloan.lawyer.domain.model.Lawyer;
import com.sheep.ezloan.lawyer.domain.repository.LawyerRepository;
import com.sheep.ezloan.lawyer.storage.entity.LawyerEntity;
import com.sheep.ezloan.support.model.DomainPage;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class LawyerRepositoryAdapter implements LawyerRepository {

    private final LawyerJpaRepository lawyerJpaRepository;

    @Override
    public Lawyer saveLawyer(Lawyer lawyer) {
        LawyerEntity lawyerEntity = LawyerEntity.builder()
            .id(lawyer.getId())
            .nickname(lawyer.getNickname())
            .name(lawyer.getName())
            .email(lawyer.getEmail())
            .introduction(lawyer.getCareer())
            .career(lawyer.getCareer())
            .certificationInfo(lawyer.getCertificationInfo())
            .isAccepted(lawyer.getIsAccepted())
            .isPublic(lawyer.getIsPublic())
            .build();
        return lawyerJpaRepository.save(lawyerEntity).toDomain();
    }

    @Override
    @Transactional
    public Lawyer update(Lawyer modifiedLawyer) {
        LawyerEntity targetLawyerEntity = lawyerJpaRepository.findByIdAndIsDeletedFalse(modifiedLawyer.getId())
            .orElseThrow(EntityNotFoundException::new);
        LawyerEntity modifiedLawyerEntity = targetLawyerEntity.update(modifiedLawyer.getNickname(),
                modifiedLawyer.getEmail(), modifiedLawyer.getIntroduction(), modifiedLawyer.getCertificationInfo(),
                modifiedLawyer.getCareer());
        return modifiedLawyerEntity.toDomain();
    }

    @Override
    public Lawyer findById(Long id) {
        return lawyerJpaRepository.findByIdAndIsDeletedFalse(id).orElseThrow(EntityNotFoundException::new).toDomain();
    }

    @Override
    @Transactional
    public Lawyer deleteById(Long id) {
        LawyerEntity targetLawyerEntity = lawyerJpaRepository.findByIdAndIsDeletedFalse(id)
            .orElseThrow(EntityNotFoundException::new);
        targetLawyerEntity.delete();
        return targetLawyerEntity.toDomain();
    }

    @Override
    public DomainPage<Lawyer> searchLawyers(String searchQuery, String sortBy, String direction, int page, int size) {
        Sort sort = Sort.by(Sort.Direction.fromString(direction), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Lawyer> lawyerPage = lawyerJpaRepository.searchLawyers(searchQuery, pageable).map(LawyerEntity::toDomain);

        return DomainPage.of(lawyerPage.getContent(), lawyerPage.getTotalElements(), lawyerPage.getTotalPages(),
                lawyerPage.getNumber(), lawyerPage.getSize(), lawyerPage.hasNext());
    }

}
