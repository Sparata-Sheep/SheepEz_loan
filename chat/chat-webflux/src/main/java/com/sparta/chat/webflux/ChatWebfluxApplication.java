package com.sparta.chat.webflux;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;

// WebFlux 모듈에서는 JPA 자동 구성 및 MVC 자동구성 제외
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class,
        WebMvcAutoConfiguration.class })
public class ChatWebfluxApplication {

    public static void main(String[] args) {
        SpringApplication.run(ChatWebfluxApplication.class, args);
    }

}
