package com.sheep.ezloan.lawyer.storage.implement;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.sheep.ezloan.lawyer.storage.entity.LawyerEntity;

public interface LawyerCustomRepository {

    Page<LawyerEntity> searchLawyers(String searchQuery, Pageable pageable);

}
