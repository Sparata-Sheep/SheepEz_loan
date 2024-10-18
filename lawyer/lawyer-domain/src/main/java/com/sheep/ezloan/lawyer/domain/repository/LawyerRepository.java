package com.sheep.ezloan.lawyer.domain.repository;

import com.sheep.ezloan.lawyer.domain.model.Lawyer;
import com.sheep.ezloan.support.model.DomainPage;

public interface LawyerRepository {

    Lawyer saveLawyer(Lawyer lawyer);

    Lawyer update(Lawyer lawyer);

    Lawyer findById(Long id);

    Lawyer findByIdNotAccepted(Long id);

    Lawyer deleteById(Long id);

    DomainPage<Lawyer> searchLawyers(String searchQuery, String sortBy, String direction, int page, int size,
            Boolean isAccepted);

    Lawyer updateIsAccepted(Lawyer lawyer);

}
