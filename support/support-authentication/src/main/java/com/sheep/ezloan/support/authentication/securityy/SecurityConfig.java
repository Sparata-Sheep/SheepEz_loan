package com.sheep.ezloan.support.authentication.securityy;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer.FrameOptionsConfig;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.sheep.ezloan.support.authentication.securityy.filter.CustomAuthenticationFilter;

@Configuration
@EnableMethodSecurity
@ComponentScan("com.sheep.ezloan.support.authentication")
public class SecurityConfig {

    private final CustomAuthenticationFilter customAuthenticationFilter;

    public SecurityConfig(CustomAuthenticationFilter customAuthenticationFilter, RoleHierarchy roleHierarchy) {
        this.customAuthenticationFilter = customAuthenticationFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.ignoringRequestMatchers("/h2-console/**")) // H2 콘솔에 대해
                                                                          // CSRF 비활성화
            .authorizeHttpRequests(authz -> authz.requestMatchers("/h2-console/**", "/feign/**", "/actuator/**")
                .permitAll() // H2 콘솔에 대한 접근 허용
                .anyRequest()
                .authenticated() // 다른 모든 요청은 인증 필요
            )
            .headers(headers -> headers.frameOptions(FrameOptionsConfig::disable) // 프레임
                                                                                  // 옵션
                                                                                  // 비활성화하여
                                                                                  // H2 콘솔
                                                                                  // 접근 허용
            )
            .addFilterBefore(customAuthenticationFilter, UsernamePasswordAuthenticationFilter.class); // 커스텀
                                                                                                      // 필터
                                                                                                      // 추가

        return http.build();
    }

}
