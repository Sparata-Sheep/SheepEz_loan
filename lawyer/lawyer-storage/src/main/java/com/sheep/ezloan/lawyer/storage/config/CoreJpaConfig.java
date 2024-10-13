package com.sheep.ezloan.lawyer.storage.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EntityScan(basePackages = "com.sheep.ezloan.lawyer")
@EnableJpaRepositories(basePackages = "com.sheep.ezloan.lawyer")
public class CoreJpaConfig {

}
