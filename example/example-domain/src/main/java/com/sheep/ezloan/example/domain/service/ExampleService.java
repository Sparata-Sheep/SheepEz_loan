package com.sheep.ezloan.example.domain.service;

import org.springframework.stereotype.Service;

import com.sheep.ezloan.example.domain.model.Example;
import com.sheep.ezloan.example.domain.repository.ExampleRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ExampleService {

    private final ExampleRepository exampleRepository;

    public Example createExample(Example example) {
        return exampleRepository.save(example);
    }

}
