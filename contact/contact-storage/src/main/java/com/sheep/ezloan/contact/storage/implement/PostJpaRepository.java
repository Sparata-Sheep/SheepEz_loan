package com.sheep.ezloan.contact.storage.implement;

import com.sheep.ezloan.contact.storage.entity.PostEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface PostJpaRepository extends JpaRepository<PostEntity, UUID> {

    Page<PostEntity> findAllByIsDeletedFalse(Pageable pageable);

    Optional<PostEntity> findByPostUuidAndIsDeletedFalse(UUID postUuid);

}
