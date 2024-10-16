package com.sheep.ezloan.lawyer.storage.implement;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sheep.ezloan.lawyer.storage.entity.ReviewEntity;

public interface ReviewJpaRepository extends JpaRepository<ReviewEntity, UUID>, ReviewCustomRepository {

    List<ReviewEntity> findByLawyerIdAndIsDeletedFalse(Long lawyerId);

}
