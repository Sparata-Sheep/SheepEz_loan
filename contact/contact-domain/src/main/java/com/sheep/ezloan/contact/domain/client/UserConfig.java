package com.sheep.ezloan.contact.domain.client;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@EnableFeignClients(basePackages = "com.sheep.ezloan.contact.domain")
@Configuration
public class UserConfig {

}
