package com.sheep.ezloan.contact.storage.implement;

import com.sheep.ezloan.contact.storage.entity.ContractEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ContractJpaRepository extends JpaRepository<ContractEntity, UUID> {

}
