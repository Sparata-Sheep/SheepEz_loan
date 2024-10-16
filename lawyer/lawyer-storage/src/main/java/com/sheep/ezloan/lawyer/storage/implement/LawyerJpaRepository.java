package com.sheep.ezloan.lawyer.storage.implement;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sheep.ezloan.lawyer.storage.entity.LawyerEntity;

public interface LawyerJpaRepository extends JpaRepository<LawyerEntity, Long>, LawyerCustomRepository {

    Optional<LawyerEntity> findByIdAndIsDeletedFalse(Long id);

    Optional<LawyerEntity> findByIdAndIsDeletedFalseAndIsAcceptedTrue(Long id);

}
