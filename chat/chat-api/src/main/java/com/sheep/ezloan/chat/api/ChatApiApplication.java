package com.sheep.ezloan.chat.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.web.reactive.WebFluxAutoConfiguration;
import org.springframework.boot.autoconfigure.web.reactive.function.client.WebClientAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

// JPA 모듈에서는 WebFlux 자동 구성을 제외
@SpringBootApplication(exclude = { WebFluxAutoConfiguration.class, WebClientAutoConfiguration.class })
@ComponentScan(basePackages = { "com.sheep.ezloan.chat.storage", "com.sheep.ezloan.client.post" })
@EntityScan(basePackages = "com.sheep.ezloan.chat.storage.entity")
public class ChatApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ChatApiApplication.class, args);
    }

}
