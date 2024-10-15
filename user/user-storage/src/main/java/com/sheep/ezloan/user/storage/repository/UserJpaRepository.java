package com.sheep.ezloan.user.storage.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sheep.ezloan.user.storage.entity.UserEntity;

public interface UserJpaRepository extends JpaRepository<UserEntity, Long> {

    boolean existsByUsername(String username);

    Optional<UserEntity> findByUsername(String username);

    Optional<UserEntity> findByUserIdAndIsDeletedFalse(Long userId);

    List<UserEntity> findAllByIsDeletedFalse();

    List<UserEntity> findByUsernameContainingAndIsDeletedFalse(String username);

}
