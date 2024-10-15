package com.sheep.ezloan.support.authentication.securityy;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.authority.mapping.SimpleAuthorityMapper;

@Configuration
public class AuthorityMapperConfig {

    @Bean
    public SimpleAuthorityMapper authorityMapper() {
        SimpleAuthorityMapper authorityMapper = new SimpleAuthorityMapper();
        authorityMapper.setConvertToUpperCase(true); // 권한을 대문자로 변환
        authorityMapper.setDefaultAuthority("ROLE_USER"); // 기본 권한 설정
        return authorityMapper;
    }

}
