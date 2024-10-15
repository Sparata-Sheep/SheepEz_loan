package com.sheep.ezloan.user.domain;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.sheep.ezloan.user.storage.entity.RoleType;
import com.sheep.ezloan.user.storage.entity.UserEntity;
import com.sheep.ezloan.user.storage.repository.UserJpaRepository;

@Configuration
public class AdminAccountInitializer {

    private final UserJpaRepository userRepository;

    @Value("${admin.default.password}")
    private String adminPassword;

    public AdminAccountInitializer(UserJpaRepository userRepository) {
        this.userRepository = userRepository;

    }

    @Bean
    public CommandLineRunner initAdminAccount() {
        return args -> {
            // admin 계정이 이미 있는지 확인

            UserEntity admin = userRepository.findByUsername("admin").orElse(null);

            // 기본 admin 계정 생성
            if (admin == null) {
                admin = UserEntity.builder()
                    .username("admin")
                    .password(adminPassword) // 초기 비밀번호 설정
                    .role(RoleType.MASTER)// 최고 권한 부여
                    .build();
                userRepository.save(admin);
            }
            System.out.println("Master 계정이 생성되었습니다.");
        };
    }

}
