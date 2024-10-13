package com.sheep.ezloan.example.api.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sheep.ezloan.example.domain.model.Example;
import com.sheep.ezloan.example.domain.service.ExampleService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/examples")
public class ExampleController {

    private final ExampleService exampleService;

    @PostMapping
    public String create() {
        exampleService.createExample(new Example());
        return "Created";
    }

}
