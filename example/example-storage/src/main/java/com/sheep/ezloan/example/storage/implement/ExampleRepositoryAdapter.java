package com.sheep.ezloan.example.storage.implement;

import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.sheep.ezloan.example.domain.model.Example;
import com.sheep.ezloan.example.domain.repository.ExampleRepository;
import com.sheep.ezloan.example.storage.entity.ExampleEntity;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ExampleRepositoryAdapter implements ExampleRepository {

    private final ExampleJpaRepository exampleJpaRepository;

    @Override
    public Example save(Example example) {
        ExampleEntity entity = new ExampleEntity(UUID.randomUUID());
        exampleJpaRepository.save(entity);

        return null;
    }

}
